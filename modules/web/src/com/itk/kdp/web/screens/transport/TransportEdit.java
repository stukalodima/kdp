package com.itk.kdp.web.screens.transport;

import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.itk.kdp.entity.Transport;
import com.itk.kdp.web.screens.form.StandardEditorITK;

@UiController("kdp_Transport.edit")
@UiDescriptor("transport-edit.xml")
@EditedEntityContainer("transportDc")
@LoadDataBeforeShow
public class TransportEdit extends StandardEditorITK<Transport> {

}