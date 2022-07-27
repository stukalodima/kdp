package com.itk.kdp.web.screens.organizations;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.service.CompanyService;

import javax.inject.Inject;
import java.io.IOException;

@UiController("kdp_Organizations.browse")
@UiDescriptor("organizations-browse.xml")
@LookupComponent("organizationsesTable")
@LoadDataBeforeShow
public class OrganizationsBrowse extends StandardLookup<Organizations> {
    @Inject
    private CompanyService companyService;
    @Inject
    private Dialogs dialogs;
    @Inject
    private Messages messages;
    @Inject
    private CollectionLoader<Organizations> organizationsesDl;

    @Subscribe("organizationsesTable.fillFromExternal")
    public void onOrganizationsesTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            companyService.getCompanyListFromExternal();
        } catch (IOException e) {
            dialogs.createMessageDialog()
                    .withCaption(messages.getMessage(OrganizationsBrowse.class, "messages.getCompanyListError.caption"))
                    .withMessage(messages.getMessage(OrganizationsBrowse.class, "messages.getCompanyListError.text")
                            + "\n" + e.getMessage())
                    .show();
        }
        organizationsesDl.load();
    }
}