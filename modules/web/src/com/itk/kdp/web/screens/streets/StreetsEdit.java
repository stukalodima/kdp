package com.itk.kdp.web.screens.streets;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Streets;

@UiController("kdp_Streets.edit")
@UiDescriptor("streets-edit.xml")
@EditedEntityContainer("streetsDc")
@LoadDataBeforeShow
public class StreetsEdit extends StandardEditor<Streets> {
}