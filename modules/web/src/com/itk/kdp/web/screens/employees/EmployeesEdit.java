package com.itk.kdp.web.screens.employees;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;

import javax.inject.Inject;
import java.util.Objects;

@UiController("kdp_Employees.edit")
@UiDescriptor("employees-edit.xml")
@EditedEntityContainer("employeesDc")
@LoadDataBeforeShow
public class EmployeesEdit extends StandardEditor<Employees> {
    @Inject
    private CollectionLoader<Departments> departmentsesDl;
    @Inject
    private DataManager dataManager;

    @Subscribe("companyField")
    public void onCompanyFieldValueChange(HasValue.ValueChangeEvent<Organizations> event) {

        departmentsesDl.setParameter( "organization", event.getValue());
        departmentsesDl.load();
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        departmentsesDl.setParameter("organization", (Objects.isNull(getEditedEntity().getCompany()) ? dataManager.create(Organizations.class) : getEditedEntity().getCompany()));
        departmentsesDl.load();
    }
}