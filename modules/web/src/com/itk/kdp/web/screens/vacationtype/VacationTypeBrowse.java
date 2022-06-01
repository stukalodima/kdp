package com.itk.kdp.web.screens.vacationtype;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationType;

@UiController("kdp_VacationType.browse")
@UiDescriptor("vacation-type-browse.xml")
@LookupComponent("vacationTypesTable")
@LoadDataBeforeShow
public class VacationTypeBrowse extends StandardLookup<VacationType> {
}