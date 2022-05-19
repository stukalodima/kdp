package com.itk.kdp.web.screens.country;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Country;

@UiController("kdp_Country.browse")
@UiDescriptor("country-browse.xml")
@LookupComponent("countriesTable")
@LoadDataBeforeShow
public class CountryBrowse extends StandardLookup<Country> {
}