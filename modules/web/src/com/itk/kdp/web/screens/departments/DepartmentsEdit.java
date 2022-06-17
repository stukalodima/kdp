package com.itk.kdp.web.screens.departments;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("kdp_Departments.edit")
@UiDescriptor("departments-edit.xml")
@EditedEntityContainer("departmentsDc")
@LoadDataBeforeShow
public class DepartmentsEdit extends StandardEditor<Departments> {

    @Inject
    private CollectionLoader<Departments> departmentsesDl;
    @Inject
    private DataManager dataManager;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        List<Departments> departments = new ArrayList<>();
        getListDepartment(departments, getEditedEntity());

        departmentsesDl.setParameter("department", departments);
        departmentsesDl.load();
    }

    private void getListDepartment(List<Departments> departmentsList, Departments editedEntity) {
        List<Departments> list = dataManager.load(Departments.class)
                .query("select e from kdp_Departments e where e.parentId = :parentId")
                .parameter("parentId", editedEntity)
                .view("departments-parent-id")
                .list();

        list.forEach(e -> getListDepartment(departmentsList, e));

        departmentsList.add(editedEntity);
    }
}