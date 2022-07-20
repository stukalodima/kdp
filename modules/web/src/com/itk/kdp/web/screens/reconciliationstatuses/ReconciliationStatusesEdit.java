package com.itk.kdp.web.screens.reconciliationstatuses;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.ReconciliationStatuses;
import com.itk.kdp.web.screens.purpose.PurposeEdit;

import javax.inject.Inject;

@UiController("kdp_ReconciliationStatuses.edit")
@UiDescriptor("reconciliation-statuses-edit.xml")
@EditedEntityContainer("reconciliationStatusesDc")
@LoadDataBeforeShow
public class ReconciliationStatusesEdit extends StandardEditor<ReconciliationStatuses> {
    @Inject
    private EntityStates entityStates;
    @Inject
    private Messages messages;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        updateFromCaption();
    }

    private void updateFromCaption(){
        if (entityStates.isNew(getEditedEntity())){
            this.getWindow().setCaption(
                    messages.getMessage(ReconciliationStatusesEdit.class, "Статус согласования")
                            + ": "
                            + messages.getMessage(ReconciliationStatusesEdit.class, "(cоздание)")
            );
        } else {
            this.getWindow().setCaption(
                    messages.getMessage(ReconciliationStatusesEdit.class, "Статус согласования")
                            + ": "
                            + getEditedEntity().getName()
            );
        }
    }
}