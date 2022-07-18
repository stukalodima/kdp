package com.itk.kdp.web.screens.businesstrip;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.BusinessTrip;

@UiController("kdp_BusinessTrip.browse")
@UiDescriptor("business-trip-browse.xml")
@LookupComponent("businessTripsTable")
@LoadDataBeforeShow
public class BusinessTripBrowse extends StandardLookup<BusinessTrip> {
}