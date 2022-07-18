package com.itk.kdp.web.screens.purpose;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Purpose;

@UiController("kdp_Purpose.browse")
@UiDescriptor("purpose-browse.xml")
@LookupComponent("purposesTable")
@LoadDataBeforeShow
public class PurposeBrowse extends StandardLookup<Purpose> {
}