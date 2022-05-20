package com.itk.kdp.web.screens.country;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Country;

@UiController("kdp_Country.edit")
@UiDescriptor("country-edit.xml")
@EditedEntityContainer("countryDc")
@LoadDataBeforeShow
public class CountryEdit extends StandardEditor<Country> {
}