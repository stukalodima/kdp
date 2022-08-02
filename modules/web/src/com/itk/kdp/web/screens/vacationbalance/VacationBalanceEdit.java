package com.itk.kdp.web.screens.vacationbalance;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationBalance;
import com.itk.kdp.web.screens.form.StandardEditorITK;

@UiController("kdp_VacationBalance.edit")
@UiDescriptor("vacation-balance-edit.xml")
@EditedEntityContainer("vacationBalanceDc")
@LoadDataBeforeShow
public class VacationBalanceEdit extends StandardEditorITK<VacationBalance> {
}