package com.itk.kdp.web.screens.vacationbalance;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationBalance;
import com.itk.kdp.service.VacationBalanceService;
import com.itk.kdp.web.screens.form.DialogsITK;

import javax.inject.Inject;
import java.io.IOException;

@UiController("kdp_VacationBalance.browse")
@UiDescriptor("vacation-balance-browse.xml")
@LookupComponent("vacationBalancesTable")
@LoadDataBeforeShow
public class VacationBalanceBrowse extends StandardLookup<VacationBalance> {
    @Inject
    private VacationBalanceService vacationBalanceService;
    @Inject
    private Dialogs dialogs;

    @Subscribe("vacationBalancesTable.fillFromExternal")
    public void onVacationBalancesTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            vacationBalanceService.getVacationBalanceListFromExternal();
            DialogsITK.getDialogForImportSuccess(dialogs, VacationBalanceBrowse.class);
        } catch (IOException e) {
            DialogsITK.getDialogForImportError(dialogs, e, VacationBalanceBrowse.class);
        }
    }

}