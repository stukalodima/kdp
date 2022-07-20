package com.itk.kdp.web.screens.organizations;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.web.screens.departments.DepartmentsEdit;

import javax.inject.Inject;

@UiController("kdp_Organizations.edit")
@UiDescriptor("organizations-edit.xml")
@EditedEntityContainer("organizationsDc")
@LoadDataBeforeShow
public class OrganizationsEdit extends StandardEditor<Organizations> {


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
                    messages.getMessage(OrganizationsEdit.class, "Организация")
                            + ": "
                            + messages.getMessage(OrganizationsEdit.class, "(cоздание)")
            );
        } else {
            this.getWindow().setCaption(
                    messages.getMessage(OrganizationsEdit.class, "Организация")
                            + ": "
                            + getEditedEntity().getFullName()
            );
        }
    }

}