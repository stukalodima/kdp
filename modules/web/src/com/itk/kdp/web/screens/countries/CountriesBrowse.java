package com.itk.kdp.web.screens.countries;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Countries;

@UiController("kdp_Countries.browse")
@UiDescriptor("countries-browse.xml")
@LookupComponent("countriesesTable")
@LoadDataBeforeShow
public class CountriesBrowse extends StandardLookup<Countries> {
}