package com.itk.kdp.web.screens.vacationtype;

import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationType;
import com.itk.kdp.web.screens.form.StandardEditorITK;

import javax.inject.Inject;

@UiController("kdp_VacationType.edit")
@UiDescriptor("vacation-type-edit.xml")
@EditedEntityContainer("vacationTypeDc")
@LoadDataBeforeShow
public class VacationTypeEdit extends StandardEditorITK<VacationType> {
    @Inject
    private EntityStates entityStates;
    @Inject
    private UniqueNumbersService uniqueNumbersService;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            getEditedEntity().setCode(getEditedEntity().generateNewCode().toString());
        }
    }

}