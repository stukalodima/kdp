package com.itk.kdp.web.screens.vacationtype;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationType;

@UiController("kdp_VacationType.edit")
@UiDescriptor("vacation-type-edit.xml")
@EditedEntityContainer("vacationTypeDc")
@LoadDataBeforeShow
public class VacationTypeEdit extends StandardEditor<VacationType> {
}