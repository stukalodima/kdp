package com.itk.kdp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haulmont.cuba.core.global.DataManager;
import com.itk.kdp.config.RestApiConfig;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Position;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service(DepartmentService.NAME)
public class DepartmentServiceBean implements DepartmentService {

    @Inject
    private RestClientService restClientService;
    @Inject
    private DataManager dataManager;
    @Inject
    private CompanyService companyService;
    @Inject
    private RestApiConfig restApiConfig;
    @Inject
    private EmployeeService employeeService;

    @Override
    public void getDepartmentListFromExternal() throws IOException {
        String connectString = restApiConfig.getRestApiDepartmentService();
        String jsonString = restClientService.callGetMethod(connectString);
        if (!jsonString.isEmpty()) {
            parseJsonString(jsonString);
        }
    }

    @Override
    public Departments getDepartmentByCode(String code) {
        List<Departments> departmentsList = dataManager.load(Departments.class)
                .query("select e from kdp_Departments e where e.department1cId = :code")
                .parameter("code", code)
                .view("departments-parent-id")
                .list();
        Departments departments = null;
        if (departmentsList.size() > 0) {
            departments = departmentsList.get(0);
        }
        return departments;
    }

    @Override
    public Departments getDepartmentById(String id) {
        UUID uuid = UUID.fromString(id);
        List<Departments> departmentsList = dataManager.load(Departments.class)
                .query("select e from kdp_Position e where e.id = :id")
                .parameter("id", uuid)
                .view("departments-parent-id")
                .list();
        Departments departments = null;
        if (departmentsList.size() > 0) {
            departments = departmentsList.get(0);
        }
        return departments;
    }

    private void parseJsonString(String jsonString) {
        JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
        HashMap<String, String> departmentMap = new HashMap<>();
        for (JsonElement jsonElement:jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //from accounts
            departmentMap.put("companyId", jsonObject.getAsJsonPrimitive("companyId").getAsString());
            departmentMap.put("departmentUa", jsonObject.getAsJsonPrimitive("departmentUa").getAsString());
            departmentMap.put("departmentRu", jsonObject.getAsJsonPrimitive("departmentRu").getAsString());
            departmentMap.put("name", jsonObject.getAsJsonPrimitive("departmentUa").getAsString());
            departmentMap.put("department1cId", jsonObject.getAsJsonPrimitive("departmentId").getAsString());
            departmentMap.put("pId", jsonObject.getAsJsonPrimitive("pId").getAsString());
            departmentMap.put("managerId", jsonObject.getAsJsonPrimitive("approvalManager").getAsString());
            departmentMap.put("GUID", jsonObject.getAsJsonPrimitive("GUID").getAsString());

            fillCompanyEntity(departmentMap);
        }
    }

    private void fillCompanyEntity(HashMap<String, String> departmentMap){
        Departments department = getDepartmentByCode(departmentMap.get("companyId") + "$" + departmentMap.get("department1cId"));

        if (Objects.isNull(department)) {
            department = dataManager.create(Departments.class);
        }

        department.setId(UUID.fromString(departmentMap.get("GUID")));
        department.setDepartment1cId(departmentMap.get("companyId") + "$" + departmentMap.get("department1cId"));
        department.setName(departmentMap.get("name"));
        department.setDepartmentUa(departmentMap.get("departmentUa"));
        department.setDepartmentRu(departmentMap.get("departmentRu"));
        department.setParentId(getDepartmentByCode(departmentMap.get("companyId") + "$" + departmentMap.get("pId")));
        department.setManagerId(employeeService.getEmployeeByCode(departmentMap.get("managerId")));
        department.setOrganizationsId(companyService.getCompanyByCode(departmentMap.get("companyId")));

        dataManager.commit(department);
    }
}