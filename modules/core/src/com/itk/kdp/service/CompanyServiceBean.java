package com.itk.kdp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haulmont.cuba.core.global.DataManager;
import com.itk.kdp.config.RestApiConfig;
import com.itk.kdp.entity.Organizations;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service(CompanyService.NAME)
public class CompanyServiceBean implements CompanyService {

    @Inject
    private RestClientService restClientService;
    @Inject
    private DataManager dataManager;
    @Inject
    private RestApiConfig restApiConfig;

    @Override
    public void getCompanyListFromExternal() throws IOException {
        String connectString = restApiConfig.getRestApiCompanyService();
        String jsonString = restClientService.callGetMethod(connectString);
        if (!jsonString.isEmpty()) {
            parseJsonString(jsonString);
        }
    }

    @Override
    public Organizations getCompanyByCode(String code) {
        List<Organizations> companyList = dataManager.load(Organizations.class)
                .query("select e from kdp_Organizations e where e.organizations1cId = :code")
                .parameter("code", code)
                .view("_local")
                .list();
        Organizations company = null;
        if (companyList.size() > 0) {
            company = companyList.get(0);
        }
        return company;
    }

    @Override
    public Organizations getCompanyById(String id) {
        UUID uuid = UUID.fromString(id);
        List<Organizations> companyList = dataManager.load(Organizations.class)
                .query("select e from kdp_Organizations e where e.id = :id")
                .parameter("id", uuid)
                .view("_local")
                .list();
        Organizations company = null;
        if (companyList.size() > 0) {
            company = companyList.get(0);
        }
        return company;
    }

    private void parseJsonString(String jsonString) {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        HashMap<String, String> companyMap = new HashMap<>();
        for (JsonElement jsonElement:jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //from accounts
            companyMap.put("id", jsonObject.getAsJsonPrimitive("companyId").getAsString());
            companyMap.put("organizationsUa", jsonObject.getAsJsonPrimitive("companyUa").getAsString());
            companyMap.put("organizationsRu", jsonObject.getAsJsonPrimitive("companyRu").getAsString());
            companyMap.put("fullName", jsonObject.getAsJsonPrimitive("companyUa").getAsString());
            companyMap.put("managerId", jsonObject.getAsJsonPrimitive("managerId").getAsString());
            companyMap.put("GUID", jsonObject.getAsJsonPrimitive("GUID").getAsString());

            fillCompanyEntity(companyMap);
        }
    }

    private void fillCompanyEntity(HashMap<String, String> companyMap){
        Organizations company = getCompanyById(companyMap.get("GUID"));

        if (Objects.isNull(company)) {
            company = dataManager.create(Organizations.class);
        }

        company.setId(UUID.fromString(companyMap.get("GUID")));
        company.setFullName(companyMap.get("organizationsUa"));
        company.setOrganizationsUa(companyMap.get("organizationsUa"));
        company.setOrganizationsRu(companyMap.get("organizationsRu"));
        company.setOrganizations1cId(companyMap.get("id"));

        dataManager.commit(company);
    }
}