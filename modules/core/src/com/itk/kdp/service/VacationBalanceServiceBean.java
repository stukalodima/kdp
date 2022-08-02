package com.itk.kdp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haulmont.cuba.core.global.DataManager;
import com.itk.kdp.config.RestApiConfig;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.VacationBalance;
import com.itk.kdp.entity.VacationType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service(VacationBalanceService.NAME)
public class VacationBalanceServiceBean implements VacationBalanceService {

    @Inject
    private RestClientService restClientService;
    @Inject
    private DataManager dataManager;
    @Inject
    private RestApiConfig restApiConfig;
    @Inject
    private EmployeeService employeeService;
    @Inject
    private VacationTypeService vacationTypeService;
    @Inject
    private CompanyService companyService;

    @Override
    public void getVacationBalanceListFromExternal() throws IOException {
        String connectString = restApiConfig.getRestApiVacationBalanceService();

        for (Organizations organizations : companyService.getCompanyListByActive()){
            String jsonString = restClientService.callGetMethod(connectString + "?companyId=" + organizations.getOrganizations1cId(), true);
            if (!jsonString.isEmpty()) {
                parseJsonString(jsonString);
            }
        }

    }

    @Override
    public VacationBalance getVacationBalanceByEmployeeAndVacationType(Employees employee, VacationType vacationType) {
        List<VacationBalance> vacationBalanceList = dataManager.load(VacationBalance.class)
                .query("select e from kdp_VacationBalance e where e.employee = :employee and e.vacationType = :vacationType")
                .parameter("employee", employee)
                .parameter("vacationType", vacationType)
                .view("_base")
                .list();

        VacationBalance vacationBalance = null;
        if (vacationBalanceList.size() > 0) {
            vacationBalance = vacationBalanceList.get(0);
        }
        return vacationBalance;
    }

    private void parseJsonString(String jsonString) {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        HashMap<String, String> vacationBalanceMap = new HashMap<>();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //from accounts
            vacationBalanceMap.put("companyId", jsonObject.getAsJsonPrimitive("companyId").getAsString());
            vacationBalanceMap.put("employeeId", jsonObject.getAsJsonPrimitive("employeeId").getAsString());
            vacationBalanceMap.put("vacationTypeId", jsonObject.getAsJsonPrimitive("vacationTypeId").getAsString());
            vacationBalanceMap.put("days", jsonObject.getAsJsonPrimitive("days").getAsString());

            fillVacationBalanceEntity(vacationBalanceMap);
        }
    }

    private void fillVacationBalanceEntity(HashMap<String, String> vacationBalanceMap) {
        Employees employees = employeeService.getEmployeeByCode(vacationBalanceMap.get("employeeId"));
        VacationType vacationType = vacationTypeService.getVacationTypeByCode(vacationBalanceMap.get("vacationTypeId"));
        VacationBalance vacationBalance = getVacationBalanceByEmployeeAndVacationType(employees, vacationType);
        if (Objects.isNull(employees) || Objects.isNull(vacationType)) {
            return;
        }
        if (Objects.isNull(vacationBalance)) {
            vacationBalance = dataManager.create(VacationBalance.class);
        }

        vacationBalance.setEmployee(employees);
        vacationBalance.setVacationType(vacationType);
        vacationBalance.setDays(Integer.parseInt(vacationBalanceMap.get("days")));

        dataManager.commit(vacationBalance);
    }
}