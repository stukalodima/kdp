package com.itk.kdp.web.screens.employee;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Employee;

@UiController("kdp_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
@LoadDataBeforeShow
public class EmployeeBrowse extends StandardLookup<Employee> {
}