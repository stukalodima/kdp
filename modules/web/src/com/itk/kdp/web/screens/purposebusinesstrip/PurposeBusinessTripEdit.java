package com.itk.kdp.web.screens.purposebusinesstrip;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.PurposeBusinessTrip;

@UiController("kdp_PurposeBusinessTrip.edit")
@UiDescriptor("purpose-business-trip-edit.xml")
@EditedEntityContainer("purposeBusinessTripDc")
@LoadDataBeforeShow
public class PurposeBusinessTripEdit extends StandardEditor<PurposeBusinessTrip> {
}