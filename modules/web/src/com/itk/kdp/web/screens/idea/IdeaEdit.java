package com.itk.kdp.web.screens.idea;

import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Idea;
import com.itk.kdp.service.EmployeeService;

import javax.inject.Inject;

@UiController("kdp_Idea.edit")
@UiDescriptor("idea-edit.xml")
@EditedEntityContainer("ideaDc")
@LoadDataBeforeShow
public class IdeaEdit extends StandardEditor<Idea> {
    @Inject
    private EmployeeService employeeService;
    @Inject
    private UserSession userSession;
    @Inject
    private PickerField<Employees> authorField;
    @Inject
    private PickerField<Employees> managerField;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Idea> event) {
        Employees employees = employeeService.getEmployeeByUser(userSession.getUser());
        event.getEntity().setAuthor(employees);
        if (event.getEntity().getAuthor() != null) {
            event.getEntity().setManager(employees.getManager());
            authorField.setEditable(false);
            managerField.setEditable(false);
        }
    }
}