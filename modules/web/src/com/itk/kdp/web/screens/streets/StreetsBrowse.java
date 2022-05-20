package com.itk.kdp.web.screens.streets;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Streets;

@UiController("kdp_Streets.browse")
@UiDescriptor("streets-browse.xml")
@LookupComponent("streetsesTable")
@LoadDataBeforeShow
public class StreetsBrowse extends StandardLookup<Streets> {
}