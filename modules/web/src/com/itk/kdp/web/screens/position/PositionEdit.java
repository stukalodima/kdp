package com.itk.kdp.web.screens.position;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Position;

@UiController("kdp_Position.edit")
@UiDescriptor("position-edit.xml")
@EditedEntityContainer("positionDc")
@LoadDataBeforeShow
public class PositionEdit extends StandardEditor<Position> {
}