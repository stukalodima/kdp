package com.itk.kdp.web.screens.purposebusinesstrip;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.PurposeBusinessTrip;

@UiController("kdp_PurposeBusinessTrip.browse")
@UiDescriptor("purpose-business-trip-browse.xml")
@LookupComponent("purposeBusinessTripsTable")
@LoadDataBeforeShow
public class PurposeBusinessTripBrowse extends StandardLookup<PurposeBusinessTrip> {
}