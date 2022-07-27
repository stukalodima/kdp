package com.itk.kdp.web.screens.departments;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.service.DepartmentService;
import com.itk.kdp.web.screens.organizations.OrganizationsBrowse;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@UiController("kdp_Departments.browse")
@UiDescriptor("departments-browse.xml")
@LookupComponent("departmentsesTable")
@LoadDataBeforeShow
public class DepartmentsBrowse extends StandardLookup<Departments> {


    private Organizations organization;
    private List<Departments> departments;
    @Inject
    private CollectionLoader<Departments> departmentsesDl;
    @Inject
    private DepartmentService departmentService;
    @Inject
    private Dialogs dialogs;
    @Inject
    private Messages messages;

    public void setOrganization(Organizations organization) {
        this.organization = organization;
    }
    public void setDepartments(List<Departments> departments){this.departments = departments; }
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {

        departmentsesDl.setParameter("parOrganization", organization);
        departmentsesDl.setParameter("department", departments);
        departmentsesDl.load();
    }

    @Subscribe("departmentsesTable.fillFromExternal")
    public void onDepartmentsesTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            departmentService.getDepartmentListFromExternal();
        } catch (IOException e) {
            dialogs.createMessageDialog()
                    .withCaption(messages.getMessage(OrganizationsBrowse.class, "messages.getCompanyListError.caption"))
                    .withMessage(messages.getMessage(OrganizationsBrowse.class, "messages.getCompanyListError.text")
                            + "\n" + e.getMessage())
                    .show();
        }
        departmentsesDl.load();
    }
}