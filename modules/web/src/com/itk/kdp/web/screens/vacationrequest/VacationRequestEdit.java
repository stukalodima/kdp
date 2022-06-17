package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationRequest;

@UiController("kdp_VacationRequest.edit")
@UiDescriptor("vacation-request-edit.xml")
@EditedEntityContainer("vacationRequestDc")
@LoadDataBeforeShow
public class VacationRequestEdit extends StandardEditor<VacationRequest> {
}