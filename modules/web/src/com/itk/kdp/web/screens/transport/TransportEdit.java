package com.itk.kdp.web.screens.transport;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Transport;
import com.itk.kdp.web.screens.reconciliationstatuses.ReconciliationStatusesEdit;

import javax.inject.Inject;

@UiController("kdp_Transport.edit")
@UiDescriptor("transport-edit.xml")
@EditedEntityContainer("transportDc")
@LoadDataBeforeShow
public class TransportEdit extends StandardEditor<Transport> {
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
                    messages.getMessage(TransportEdit.class, "Транспорт")
                            + ": "
                            + messages.getMessage(TransportEdit.class, "(cоздание)")
            );
        } else {
            this.getWindow().setCaption(
                    messages.getMessage(TransportEdit.class, "Транспорт")
                            + ": "
                            + getEditedEntity().getName()
            );
        }
    }

}