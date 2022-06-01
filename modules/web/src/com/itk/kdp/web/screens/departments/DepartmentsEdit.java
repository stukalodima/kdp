package com.itk.kdp.web.screens.departments;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;

@UiController("kdp_Department.edit")
@UiDescriptor("departments-edit.xml")
@EditedEntityContainer("departmentsDc")
@LoadDataBeforeShow
public class DepartmentsEdit extends StandardEditor<Departments> {
}