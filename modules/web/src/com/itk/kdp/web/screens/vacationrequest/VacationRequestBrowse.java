package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationRequest;

@UiController("kdp_VacationRequest.browse")
@UiDescriptor("vacation-request-browse.xml")
@LookupComponent("vacationRequestsTable")
@LoadDataBeforeShow
public class VacationRequestBrowse extends StandardLookup<VacationRequest> {
}