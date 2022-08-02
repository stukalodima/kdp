package com.itk.kdp.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.global.Secret;

@Source(type = SourceType.DATABASE)
public interface RestApiConfig extends Config {

    @Property("REST-API_COMPANY_SERVICE")
    @Default("http://localhost:6060/vacation/VAADIN/company.json")
    String getRestApiCompanyService();

    @Property("REST-API_DEPARTMENT_SERVICE")
    @Default("http://localhost:6060/vacation/VAADIN/department.json")
    String getRestApiDepartmentService();

    @Property("REST-API_EMPLOYEE_SERVICE")
    @Default("http://localhost:6060/vacation/VAADIN/employee.json")
    String getRestApiEmployeeService();

    @Property("REST-API_POSITION_SERVICE")
    @Default("http://localhost:6060/vacation/VAADIN/position.json")
    String getRestApiPositionService();

    @Property("REST-API_VACATION_TYPE_SERVICE")
    @Default("http://172.29.32.4/sbs_zup/hs/portal/getVacationTypes?all=1")
    String getRestApiVacationTypeService();

    @Property("REST-API_COUNTRY_SERVICE")
    @Default("http://localhost:6060/vacation/VAADIN/country.json")
    String getRestApiCountryService();

    @Property("REST-API_VACATION_BALANCE_SERVICE")
    @Default("http://172.29.32.4/sbs_zup/hs/portal/getVacationBalance")
    String getRestApiVacationBalanceService();

    @Property("REST-API_USER_VALUE")
    String getRestApiUserService();

    @Property("REST-API_PASSWORD_VALUE")
    @Secret
    String getRestApiPasswordService();
}