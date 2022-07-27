package com.itk.kdp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haulmont.cuba.core.global.DataManager;
import com.itk.kdp.config.RestApiConfig;
import com.itk.kdp.entity.Position;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service(PositionService.NAME)
public class PositionServiceBean implements PositionService {

    @Inject
    private RestClientService restClientService;
    @Inject
    private DataManager dataManager;
    @Inject
    private CompanyService companyService;
    @Inject
    private RestApiConfig restApiConfig;

    @Override
    public void getPositionListFromExternal() throws IOException {
        String connectString = restApiConfig.getRestApiPositionService();
        String jsonString = restClientService.callGetMethod(connectString);
        if (!jsonString.isEmpty()) {
            parseJsonString(jsonString);
        }
    }

    @Override
    public Position getPositionByCode(String code) {
        List<Position> positionList = dataManager.load(Position.class)
                .query("select e from kdp_Position e where e.position1cId = :code")
                .parameter("code", code)
                .view("_local")
                .list();
        Position position = null;
        if (positionList.size() > 0) {
            position = positionList.get(0);
        }
        return position;
    }

    @Override
    public Position getPositionById(String id) {
        UUID uuid = UUID.fromString(id);
        List<Position> positionList = dataManager.load(Position.class)
                .query("select e from kdp_Position e where e.id = :id")
                .parameter("id", uuid)
                .view("_local")
                .list();
        Position position = null;
        if (positionList.size() > 0) {
            position = positionList.get(0);
        }
        return position;
    }

    private void parseJsonString(String jsonString) {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        HashMap<String, String> positionMap = new HashMap<>();
        for (JsonElement jsonElement:jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //from accounts
            positionMap.put("companyId", jsonObject.getAsJsonPrimitive("companyId").getAsString());
            positionMap.put("positionUa", jsonObject.getAsJsonPrimitive("positionUa").getAsString());
            positionMap.put("positionRu", jsonObject.getAsJsonPrimitive("positionRu").getAsString());
            positionMap.put("name", jsonObject.getAsJsonPrimitive("positionUa").getAsString());
            positionMap.put("position1cId", jsonObject.getAsJsonPrimitive("positionId").getAsString());

            fillCompanyEntity(positionMap);
        }
    }

    private void fillCompanyEntity(HashMap<String, String> positionMap){
        Position position = getPositionByCode(positionMap.get("companyId") + "$" + positionMap.get("position1cId"));

        if (Objects.isNull(position)) {
            position = dataManager.create(Position.class);
        }

        position.setPosition1cId(positionMap.get("companyId") + "$" + positionMap.get("position1cId"));
        position.setName(positionMap.get("name"));
        position.setPositionUa(positionMap.get("positionRu"));
        position.setPositionRu(positionMap.get("positionRu"));
        position.setOrganizationsId(companyService.getCompanyByCode(positionMap.get("companyId")));

        dataManager.commit(position);
    }
}