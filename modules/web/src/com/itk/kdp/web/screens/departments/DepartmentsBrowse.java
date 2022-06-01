package com.itk.kdp.web.screens.departments;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;

@UiController("kdp_Department.browse")
@UiDescriptor("departments-browse.xml")
@LookupComponent("departmentsesTable")
@LoadDataBeforeShow
public class DepartmentsBrowse extends StandardLookup<Departments> {
}