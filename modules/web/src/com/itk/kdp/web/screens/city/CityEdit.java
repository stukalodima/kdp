package com.itk.kdp.web.screens.city;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.City;

@UiController("kdp_City.edit")
@UiDescriptor("city-edit.xml")
@EditedEntityContainer("cityDc")
@LoadDataBeforeShow
public class CityEdit extends StandardEditor<City> {
}