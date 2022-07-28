package com.itk.kdp.web.screens.organizations;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.service.CompanyService;
import com.itk.kdp.web.screens.form.DialogsITK;

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
    private CollectionLoader<Organizations> organizationsesDl;

    @Subscribe("organizationsesTable.fillFromExternal")
    public void onOrganizationsesTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            companyService.getCompanyListFromExternal();
            DialogsITK.getDialogForImportSuccess(
                    dialogs, OrganizationsBrowse.class
            ).show();
        } catch (IOException e) {
            DialogsITK.getDialogForImportError(
                    dialogs, e, OrganizationsBrowse.class
            ).show();
        }
        organizationsesDl.load();
    }
}