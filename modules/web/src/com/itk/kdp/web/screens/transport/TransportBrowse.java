package com.itk.kdp.web.screens.transport;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Transport;

@UiController("kdp_Transport.browse")
@UiDescriptor("transport-browse.xml")
@LookupComponent("transportsTable")
@LoadDataBeforeShow
public class TransportBrowse extends StandardLookup<Transport> {
}