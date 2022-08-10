package com.itk.kdp.web.screens.operatoraccessrequest;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.entity.OperatorAccessRequest;
import com.itk.kdp.service.OperatorAccessService;
import com.itk.kdp.web.screens.form.StandardEditorITK;

import javax.inject.Inject;

@UiController("kdp_OperatorAccessRequest.edit")
@UiDescriptor("operator-access-request-edit.xml")
@EditedEntityContainer("operatorAccessRequestDc")
@LoadDataBeforeShow
public class OperatorAccessRequestEdit extends StandardEditorITK<OperatorAccessRequest> {
    @Inject
    private UserSession userSession;
    @Inject
    private EntityStates entityStates;
    @Inject
    private OperatorAccessService operatorAccessService;
    @Inject
    private TimeSource timeSource;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            operatorAccessService.sendEmailToServiceDesk(getEditedEntity());
        }
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<OperatorAccessRequest> event) {
        event.getEntity().setAuthor(userSession.getUser());
        event.getEntity().setStartDate(timeSource.currentTimestamp());
    }
}