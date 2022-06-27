package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.service.EmployeeOrganizationService;

import javax.inject.Inject;
import java.util.Objects;

@UiController("kdp_OrganizationSelectionForm")
@UiDescriptor("organization-selection-form.xml")
@LookupComponent("vacationRequestsTable")
public class OrganizationSelectionForm extends Screen {
    @Inject
    private CollectionLoader<Employees> vacationRequestsDl;
    @Inject
    private EmployeeOrganizationService employeeOrganizationService;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        User user = employeeOrganizationService.getUser();
        if (!Objects.isNull(user.getEmail())) {
            vacationRequestsDl.setParameter("workEmail", user.getEmail());
        } else {
            vacationRequestsDl.setParameter("workEmail", "########");
        }
        vacationRequestsDl.load();
    }

}