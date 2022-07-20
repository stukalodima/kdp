package com.itk.kdp.web.screens.vacationtype;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationType;
import com.itk.kdp.web.screens.transport.TransportEdit;

import javax.inject.Inject;

@UiController("kdp_VacationType.edit")
@UiDescriptor("vacation-type-edit.xml")
@EditedEntityContainer("vacationTypeDc")
@LoadDataBeforeShow
public class VacationTypeEdit extends StandardEditor<VacationType> {
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
                    messages.getMessage(TransportEdit.class, "Вид отпуска")
                            + ": "
                            + messages.getMessage(TransportEdit.class, "(cоздание)")
            );
        } else {
            this.getWindow().setCaption(
                    messages.getMessage(TransportEdit.class, "Вид отпуска")
                            + ": "
                            + getEditedEntity().getName()
            );
        }
    }
}