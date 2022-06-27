package com.itk.kdp.web.screens.countries;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Countries;

@UiController("kdp_Countries.edit")
@UiDescriptor("countries-edit.xml")
@EditedEntityContainer("countriesDc")
@LoadDataBeforeShow
public class CountriesEdit extends StandardEditor<Countries> {
}