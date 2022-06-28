package com.itk.kdp.web.screens.departments;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Organizations;

import javax.inject.Inject;

@UiController("kdp_Departments.browse")
@UiDescriptor("departments-browse.xml")
@LookupComponent("departmentsesTable")
@LoadDataBeforeShow
public class DepartmentsBrowse extends StandardLookup<Departments> {

    private Organizations organization;
    @Inject
    private CollectionLoader<Departments> departmentsesDl;

    public void setOrganization(Organizations organization){
        this.organization = organization;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        departmentsesDl.setParameter("parOrganization", organization);
        departmentsesDl.load();
    }

    
}