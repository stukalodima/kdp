package com.itk.kdp.web.screens.region;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Region;

@UiController("kdp_Region.browse")
@UiDescriptor("region-browse.xml")
@LookupComponent("regionsTable")
@LoadDataBeforeShow
public class RegionBrowse extends StandardLookup<Region> {
}