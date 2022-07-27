package com.itk.kdp.web.screens.position;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.Position;
import com.itk.kdp.service.PositionService;
import com.itk.kdp.web.screens.organizations.OrganizationsBrowse;

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
    private Messages messages;
    @Inject
    private CollectionLoader<Position> positionsDl;

    private Organizations organization;

    @Subscribe("positionsTable.fillFromExternal")
    public void onPositionsTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            positionService.getPositionListFromExternal();
        } catch (IOException e) {
            dialogs.createMessageDialog()
                    .withCaption(messages.getMessage(OrganizationsBrowse.class, "messages.getCompanyListError.caption"))
                    .withMessage(messages.getMessage(OrganizationsBrowse.class, "messages.getCompanyListError.text")
                            + "\n" + e.getMessage())
                    .show();
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