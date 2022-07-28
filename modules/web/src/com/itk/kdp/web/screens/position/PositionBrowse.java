package com.itk.kdp.web.screens.position;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.Position;
import com.itk.kdp.service.PositionService;
import com.itk.kdp.web.screens.form.DialogsITK;

import javax.inject.Inject;
import java.io.IOException;

@UiController("kdp_Position.browse")
@UiDescriptor("position-browse.xml")
@LookupComponent("positionsTable")
@LoadDataBeforeShow
public class PositionBrowse extends StandardLookup<Position> {
    @Inject
    private PositionService positionService;
    @Inject
    private Dialogs dialogs;
    @Inject
    private CollectionLoader<Position> positionsDl;
    private Organizations organization;

    @Subscribe("positionsTable.fillFromExternal")
    public void onPositionsTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            positionService.getPositionListFromExternal();
            DialogsITK.getDialogForImportSuccess(dialogs, PositionBrowse.class).show();
        } catch (IOException e) {
            DialogsITK.getDialogForImportError(dialogs,e,PositionBrowse.class).show();
        }
        positionsDl.load();
    }

    public void setOrganization(Organizations organization) {
        this.organization = organization;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        positionsDl.setParameter("parOrganization", organization);
        positionsDl.load();
    }
}