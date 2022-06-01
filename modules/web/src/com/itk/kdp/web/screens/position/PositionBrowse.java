package com.itk.kdp.web.screens.position;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Position;

@UiController("kdp_Position.browse")
@UiDescriptor("position-browse.xml")
@LookupComponent("positionsTable")
@LoadDataBeforeShow
public class PositionBrowse extends StandardLookup<Position> {
}