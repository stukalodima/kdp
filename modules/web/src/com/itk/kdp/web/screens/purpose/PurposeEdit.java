package com.itk.kdp.web.screens.purpose;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Purpose;

@UiController("kdp_Purpose.edit")
@UiDescriptor("purpose-edit.xml")
@EditedEntityContainer("purposeDc")
@LoadDataBeforeShow
public class PurposeEdit extends StandardEditor<Purpose> {
}