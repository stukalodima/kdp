package com.itk.kdp.web.screens.transport;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Transport;

@UiController("kdp_Transport.edit")
@UiDescriptor("transport-edit.xml")
@EditedEntityContainer("transportDc")
@LoadDataBeforeShow
public class TransportEdit extends StandardEditor<Transport> {
}