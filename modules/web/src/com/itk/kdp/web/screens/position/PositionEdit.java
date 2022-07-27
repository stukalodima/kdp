package com.itk.kdp.web.screens.position;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Position;
import com.itk.kdp.web.screens.organizations.OrganizationsEdit;

import javax.inject.Inject;

@UiController("kdp_Position.edit")
@UiDescriptor("position-edit.xml")
@EditedEntityContainer("positionDc")
@LoadDataBeforeShow
public class PositionEdit extends StandardEditor<Position> {
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
                    messages.getMessage(PositionEdit.class, "Должность организации")
                            + ": "
                            + messages.getMessage(PositionEdit.class, "(cоздание)")
            );
        } else {
            this.getWindow().setCaption(
                    messages.getMessage(PositionEdit.class, "Должность организации")
                            + ": "
                            + getEditedEntity().getName()
            );
        }
    }

}