package com.itk.kdp.web.screens.departments;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Organizations;

import javax.inject.Inject;
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
}