package com.itk.kdp.web.screens.departments;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.picker.LookupAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.ActionsAwareDialogFacet;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.service.GetDepartmensService;
import com.itk.kdp.web.screens.employees.EmployeesBrowse;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

@UiController("kdp_Departments.edit")
@UiDescriptor("departments-edit.xml")
@EditedEntityContainer("departmentsDc")
@LoadDataBeforeShow
public class DepartmentsEdit extends StandardEditor<Departments> {

    @Inject
    private CollectionLoader<Departments> departmentsesDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private GetDepartmensService departmensService;
    @Inject
    private CollectionLoader<Employees> employeesesDl;
    @Inject
    private LookupPickerField<Employees> approvalManagerField;
    @Inject
    private LookupPickerField<Employees> managerIdField;
    @Inject
    private LookupPickerField<Departments> pIdField;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        departmentsesDl.setParameter("department", departmensService.getDepartmentsFilter(getEditedEntity()));
        departmentsesDl.setParameter("parOrganization", (Objects.isNull(getEditedEntity().getOrganizationsId()) ? dataManager.create(Organizations.class) : getEditedEntity().getOrganizationsId()));
        departmentsesDl.load();

        employeesesDl.setParameter("parOrganization", getEditedEntity().getOrganizationsId());
        employeesesDl.load();
    }

    @Subscribe("companyIdField")
    public void onCompanyIdFieldValueChange(HasValue.ValueChangeEvent<Organizations> event) {
        if (event.isUserOriginated()) {
            Departments editedEntity = getEditedEntity();
            editedEntity.setParentId(null);
            editedEntity.setApprovalManager(null);
            editedEntity.setManagerId(null);

            departmentsesDl.setParameter("parOrganization", (Objects.isNull(getEditedEntity().getOrganizationsId()) ? dataManager.create(Organizations.class) : getEditedEntity().getOrganizationsId()));
            departmentsesDl.setParameter("department", departmensService.getDepartmentsFilter(getEditedEntity()));
            departmentsesDl.load();
            employeesesDl.setParameter("parOrganization", getEditedEntity().getOrganizationsId());
            employeesesDl.load();
        }
    }

    @Subscribe("pIdField.lookup")
    public void onPIdFieldLookup(Action.ActionPerformedEvent event) {
        DepartmentsBrowse departmentsBrowse = screenBuilders.lookup(pIdField)
                .withScreenClass(DepartmentsBrowse.class)
                .build();
        departmentsBrowse.setOrganization(getEditedEntity().getOrganizationsId());
        departmentsBrowse.setDepartments(departmensService.getDepartmentsFilter(getEditedEntity()));
        departmentsBrowse.show();
    }

    @Subscribe("approvalManagerField.lookup")
    public void onApprovalManagerFieldLookup(Action.ActionPerformedEvent event) {

        EmployeesBrowse employeesBrowse = screenBuilders.lookup(approvalManagerField)
                .withScreenClass(EmployeesBrowse.class)
                .build();
        employeesBrowse.setOrganization(getEditedEntity().getOrganizationsId());
        employeesBrowse.show();

    }

    @Subscribe("managerIdField.lookup")
    public void onManagerIdFieldLookup(Action.ActionPerformedEvent event) {
        EmployeesBrowse employeesBrowse = screenBuilders.lookup(managerIdField)
                .withScreenClass(EmployeesBrowse.class)
                .build();
        employeesBrowse.setOrganization(getEditedEntity().getOrganizationsId());
        employeesBrowse.show();
    }
}