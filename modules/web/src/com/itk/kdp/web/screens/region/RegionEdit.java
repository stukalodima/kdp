package com.itk.kdp.web.screens.region;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Region;

@UiController("kdp_Region.edit")
@UiDescriptor("region-edit.xml")
@EditedEntityContainer("regionDc")
@LoadDataBeforeShow
public class RegionEdit extends StandardEditor<Region> {
}