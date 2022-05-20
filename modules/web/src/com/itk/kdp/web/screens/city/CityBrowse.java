package com.itk.kdp.web.screens.city;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.City;

@UiController("kdp_City.browse")
@UiDescriptor("city-browse.xml")
@LookupComponent("citiesTable")
@LoadDataBeforeShow
public class CityBrowse extends StandardLookup<City> {
}