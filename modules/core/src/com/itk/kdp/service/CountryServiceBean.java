package com.itk.kdp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haulmont.cuba.core.global.DataManager;
import com.itk.kdp.config.RestApiConfig;
import com.itk.kdp.entity.Countries;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service(CountryService.NAME)
public class CountryServiceBean implements CountryService {

    @Inject
    private RestApiConfig restApiConfig;
    @Inject
    private RestClientService restClientService;
    @Inject
    private DataManager dataManager;

    @Override
    public void getCountryListFromExternal() throws IOException {
        String connectString = restApiConfig.getRestApiCountryService();
        String jsonString = restClientService.callGetMethod(connectString);
        if (!jsonString.isEmpty()) {
            parseJsonString(jsonString);
        }
    }

    @Override
    public Countries getCountryByCode(String code) {
        List<Countries> countriesList = dataManager.load(Countries.class)
                .query("select e from kdp_Countries e where e.code = :code")
                .parameter("code", Integer.valueOf(code))
                .view("_local")
                .list();
        Countries countries = null;
        if (countriesList.size() > 0) {
            countries = countriesList.get(0);
        }
        return countries;
    }

    @Override
    public Countries getCountryById(String id) {
        UUID uuid = UUID.fromString(id);
        List<Countries> countriesList = dataManager.load(Countries.class)
                .query("select e from kdp_Countries e where e.id = :id")
                .parameter("id", uuid)
                .view("_local")
                .list();
        Countries countries = null;
        if (countriesList.size() > 0) {
            countries = countriesList.get(0);
        }
        return countries;
    }

    private void parseJsonString(String jsonString) {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        HashMap<String, String> countryMap = new HashMap<>();
        for (JsonElement jsonElement:jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //from accounts
            countryMap.put("code", jsonObject.getAsJsonPrimitive("code").getAsString());
            countryMap.put("nameUa", jsonObject.getAsJsonPrimitive("name").getAsString());
            countryMap.put("nameRu", jsonObject.getAsJsonPrimitive("name").getAsString());

            fillCountryEntity(countryMap);
        }
    }

    private void fillCountryEntity(HashMap<String, String> countryMap){
        Countries countries = getCountryByCode(countryMap.get("code"));

        if (Objects.isNull(countries)) {
            countries = dataManager.create(Countries.class);
        }

        countries.setCode(Integer.valueOf(countryMap.get("code")));
        countries.setNameUa(countryMap.get("nameUa").trim());
        countries.setNameRu(countryMap.get("nameRu").trim());

        dataManager.commit(countries);
    }
}