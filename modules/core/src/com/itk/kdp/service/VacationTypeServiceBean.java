package com.itk.kdp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haulmont.cuba.core.global.DataManager;
import com.itk.kdp.config.RestApiConfig;
import com.itk.kdp.entity.VacationType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service(VacationTypeService.NAME)
public class VacationTypeServiceBean implements VacationTypeService {

    @Inject
    private RestClientService restClientService;
    @Inject
    private DataManager dataManager;
    @Inject
    private RestApiConfig restApiConfig;

    @Override
    public void getVacationTypeListFromExternal() throws IOException {
        String connectString = restApiConfig.getRestApiVacationTypeService();
        String jsonString = restClientService.callGetMethod(connectString);
        if (!jsonString.isEmpty()) {
            parseJsonString(jsonString);
        }
    }

    @Override
    public VacationType getVacationTypeByCode(String code) {
        List<VacationType> vacationTypeList = dataManager.load(VacationType.class)
                .query("select e from kdp_VacationType e where e.vacationType1c = :code")
                .parameter("code", code)
                .view("_local")
                .list();
        VacationType vacationType = null;
        if (vacationTypeList.size() > 0) {
            vacationType = vacationTypeList.get(0);
        }
        return vacationType;
    }

    @Override
    public VacationType getVacationTypeById(String id) {
        UUID uuid = UUID.fromString(id);
        List<VacationType> vacationTypeList = dataManager.load(VacationType.class)
                .query("select e from kdp_VacationType e where e.id = :id")
                .parameter("id", uuid)
                .view("_local")
                .list();
        VacationType vacationType = null;
        if (vacationTypeList.size() > 0) {
            vacationType = vacationTypeList.get(0);
        }
        return vacationType;
    }

    private void parseJsonString(String jsonString) {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        HashMap<String, String> vacationTypeMap = new HashMap<>();
        for (JsonElement jsonElement:jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //from accounts
            vacationTypeMap.put("vacationTypeId", jsonObject.getAsJsonPrimitive("vacationTypeId").getAsString());
            vacationTypeMap.put("nameUa", jsonObject.getAsJsonPrimitive("vacationTypeUa").getAsString());
            vacationTypeMap.put("nameRu", jsonObject.getAsJsonPrimitive("vacationTypeRu").getAsString());

            fillVacationTypeEntity(vacationTypeMap);
        }
    }

    private void fillVacationTypeEntity(HashMap<String, String> vacationTypeMap){
        VacationType vacationType = getVacationTypeByCode(vacationTypeMap.get("vacationTypeId"));

        if (Objects.isNull(vacationType)) {
            vacationType = dataManager.create(VacationType.class);
            vacationType.setCode(vacationType.generateNewCode().toString());
        }

        vacationType.setVacationType1c(vacationTypeMap.get("vacationTypeId"));
        vacationType.setNameUa(vacationTypeMap.get("nameUa"));
        vacationType.setNameRu(vacationTypeMap.get("nameRu"));

        dataManager.commit(vacationType);
    }
}