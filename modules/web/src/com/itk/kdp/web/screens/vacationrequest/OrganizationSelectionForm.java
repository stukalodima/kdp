package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.service.EmployeeOrganizationService;

import javax.inject.Inject;
import java.util.Objects;

@UiController("kdp_OrganizationSelectionForm")
@UiDescriptor("organization-selection-form.xml")
@LookupComponent("organizationSelectionTable")
public class OrganizationSelectionForm extends Screen {
    @Inject
    private CollectionLoader<Employees> organizationSelectionsDl;
    @Inject
    private EmployeeOrganizationService employeeOrganizationService;


    private Employees result;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        User user = employeeOrganizationService.getUser();
        if (!Objects.isNull(user.getEmail())) {
            organizationSelectionsDl.setParameter("workEmail", user.getEmail());
        } else {
            organizationSelectionsDl.setParameter("workEmail", "########");
        }
        organizationSelectionsDl.load();
    }


    public Employees getResult(){
        return result;
    }

    @Subscribe("organizationSelectionTable")
    public void onVacationRequestsTableSelection(Table.SelectionEvent<Employees> event) {
        result = event.getSource().getSingleSelected();
        close(StandardOutcome.SELECT);
    }
}