package com.itk.kdp.web.screens.accessrequest;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.entity.AccessRequest;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.service.EmployeeService;

import javax.inject.Inject;
import java.util.Objects;

@UiController("kdp_AccessRequest.edit")
@UiDescriptor("access-request-edit.xml")
@EditedEntityContainer("accessRequestDc")
@LoadDataBeforeShow
public class AccessRequestEdit extends StandardEditor<AccessRequest> {
    @Inject
    private EmployeeService employeeService;
    @Inject
    private DataManager dataManager;
    @Inject
    private UserSession userSession;

    @Subscribe
    public void onInitEntity(InitEntityEvent<AccessRequest> event) {
        onEmployeeChange(event.getEntity(), null);
    }

    private void onEmployeeChange(AccessRequest entity, Employees pEmployees) {
        Employees employees;
        if (Objects.isNull(pEmployees)) {
            employees = employeeService.getEmployeeByUser(userSession.getUser());
        } else {
            employees = pEmployees;
        }
        if (!Objects.isNull(employees)) {
            entity.setEmployees(employees);
            entity.setCompany(employees.getCompany());
            entity.setManager(employees.getManager());
        }
    }

    @Subscribe("employeesField")
    public void onEmployeesFieldValueChange(HasValue.ValueChangeEvent<Employees> event) {
        if (event.isUserOriginated() && !Objects.isNull(event.getValue())) {
            onEmployeeChange(getEditedEntity(), event.getValue());
        }
    }
}