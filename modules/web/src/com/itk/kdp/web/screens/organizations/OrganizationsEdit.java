package com.itk.kdp.web.screens.organizations;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.web.screens.form.StandardEditorITK;

import javax.inject.Inject;

@UiController("kdp_Organizations.edit")
@UiDescriptor("organizations-edit.xml")
@EditedEntityContainer("organizationsDc")
@LoadDataBeforeShow
public class OrganizationsEdit extends StandardEditorITK<Organizations> {
    @Inject
    private EntityStates entityStates;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            getEditedEntity().setCode(getEditedEntity().generateNewCode().toString());
        }
    }

}