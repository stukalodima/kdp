package com.itk.kdp.web.screens.employees;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Employees;

@UiController("kdp_Employees.browse")
@UiDescriptor("employees-browse.xml")
@LookupComponent("employeesesTable")
@LoadDataBeforeShow
public class EmployeesBrowse extends StandardLookup<Employees> {
}