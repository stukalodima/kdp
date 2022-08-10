package com.itk.kdp.web.screens.usersfpmnt;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.UsersFpmnt;
import com.itk.kdp.web.screens.form.StandardEditorITK;

@UiController("kdp_UsersFpmnt.edit")
@UiDescriptor("users-fpmnt-edit.xml")
@EditedEntityContainer("usersFpmntDc")
@LoadDataBeforeShow
public class UsersFpmntEdit extends StandardEditorITK<UsersFpmnt> {
}