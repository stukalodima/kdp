package com.itk.kdp.web.screens.countries;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Countries;
import com.itk.kdp.service.CountryService;
import com.itk.kdp.web.screens.form.DialogsITK;

import javax.inject.Inject;
import java.io.IOException;

@UiController("kdp_Countries.browse")
@UiDescriptor("countries-browse.xml")
@LookupComponent("countriesesTable")
@LoadDataBeforeShow
public class CountriesBrowse extends StandardLookup<Countries> {
    @Inject
    private CountryService countryService;
    @Inject
    private Dialogs dialogs;

    @Subscribe("countriesesTable.fillFromExternal")
    public void onCountriesesTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            countryService.getCountryListFromExternal();
            DialogsITK.getDialogForImportSuccess(dialogs, CountriesBrowse.class).show();
        } catch (IOException e) {
            DialogsITK.getDialogForImportError(dialogs, e, CountriesBrowse.class).show();
        }
    }

}