package com.itk.kdp.web.screens.usersfpmnt;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.UsersFpmnt;

@UiController("kdp_UsersFpmnt.browse")
@UiDescriptor("users-fpmnt-browse.xml")
@LookupComponent("usersFpmntsTable")
@LoadDataBeforeShow
public class UsersFpmntBrowse extends StandardLookup<UsersFpmnt> {
}