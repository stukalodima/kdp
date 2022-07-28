package com.itk.kdp.web.screens.vacationtype;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationType;
import com.itk.kdp.service.VacationTypeService;
import com.itk.kdp.web.screens.form.DialogsITK;

import javax.inject.Inject;
import java.io.IOException;

@UiController("kdp_VacationType.browse")
@UiDescriptor("vacation-type-browse.xml")
@LookupComponent("vacationTypesTable")
@LoadDataBeforeShow
public class VacationTypeBrowse extends StandardLookup<VacationType> {
    @Inject
    private VacationTypeService vacationTypeService;
    @Inject
    private Dialogs dialogs;

    @Subscribe("vacationTypesTable.fillFromExternal")
    public void onVacationTypesTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            vacationTypeService.getVacationTypeListFromExternal();
            DialogsITK.getDialogForImportSuccess(dialogs, VacationTypeBrowse.class).show();
        } catch (IOException e) {
            DialogsITK.getDialogForImportError(dialogs, e, VacationTypeBrowse.class).show();
        }
    }

}