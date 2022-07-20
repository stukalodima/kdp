package com.itk.kdp.web.screens.purpose;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Purpose;
import com.itk.kdp.web.screens.position.PositionEdit;

import javax.inject.Inject;

@UiController("kdp_Purpose.edit")
@UiDescriptor("purpose-edit.xml")
@EditedEntityContainer("purposeDc")
@LoadDataBeforeShow
public class PurposeEdit extends StandardEditor<Purpose> {
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
                    messages.getMessage(PurposeEdit.class, "Цель поездки")
                            + ": "
                            + messages.getMessage(PurposeEdit.class, "(cоздание)")
            );
        } else {
            this.getWindow().setCaption(
                    messages.getMessage(PurposeEdit.class, "Цель поездки")
                            + ": "
                            + getEditedEntity().getName()
            );
        }
    }

}