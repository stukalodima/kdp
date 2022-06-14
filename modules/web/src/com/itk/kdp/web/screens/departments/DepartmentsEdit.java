package com.itk.kdp.web.screens.departments;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;

import javax.inject.Inject;

@UiController("kdp_Departments.edit")
@UiDescriptor("departments-edit.xml")
@EditedEntityContainer("departmentsDc")
@LoadDataBeforeShow
public class DepartmentsEdit extends StandardEditor<Departments> {

    @Inject
    private CollectionLoader<Departments> departmentsesDl;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        departmentsesDl.setParameter("department", getEditedEntity());
        departmentsesDl.load();
    }
}