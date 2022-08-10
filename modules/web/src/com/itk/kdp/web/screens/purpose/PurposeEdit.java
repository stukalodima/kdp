package com.itk.kdp.web.screens.purpose;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Purpose;
import com.itk.kdp.web.screens.form.StandardEditorITK;
import com.itk.kdp.web.screens.position.PositionEdit;

import javax.inject.Inject;

@UiController("kdp_Purpose.edit")
@UiDescriptor("purpose-edit.xml")
@EditedEntityContainer("purposeDc")
@LoadDataBeforeShow
public class PurposeEdit extends StandardEditorITK<Purpose> {

}