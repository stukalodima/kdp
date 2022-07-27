package com.itk.kdp.web.screens.accessrequest;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.AccessRequest;

@UiController("kdp_AccessRequest.browse")
@UiDescriptor("access-request-browse.xml")
@LookupComponent("accessRequestsTable")
@LoadDataBeforeShow
public class AccessRequestBrowse extends StandardLookup<AccessRequest> {
}