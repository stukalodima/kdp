package com.itk.kdp.web.screens.operatoraccessrequest;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.OperatorAccessRequest;

@UiController("kdp_OperatorAccessRequest.browse")
@UiDescriptor("operator-access-request-browse.xml")
@LookupComponent("operatorAccessRequestsTable")
@LoadDataBeforeShow
@Route("operator-access-request")
public class OperatorAccessRequestBrowse extends StandardLookup<OperatorAccessRequest> {
}