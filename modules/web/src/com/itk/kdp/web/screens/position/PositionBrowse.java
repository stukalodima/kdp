package com.itk.kdp.web.screens.position;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.Position;

import javax.inject.Inject;

@UiController("kdp_Position.browse")
@UiDescriptor("position-browse.xml")
@LookupComponent("positionsTable")
@LoadDataBeforeShow
public class PositionBrowse extends StandardLookup<Position> {

    private Organizations organization;
    @Inject
    private CollectionLoader<Position> positionsDl;

    public void setOrganization(Organizations organization) {
        this.organization = organization;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {

        positionsDl.setParameter("parOrganization", organization);
        positionsDl.load();
    }

}