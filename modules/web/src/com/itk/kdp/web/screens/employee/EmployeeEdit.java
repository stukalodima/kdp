package com.itk.kdp.web.screens.employee;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Employee;

@UiController("kdp_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
@LoadDataBeforeShow
public class EmployeeEdit extends StandardEditor<Employee> {
}