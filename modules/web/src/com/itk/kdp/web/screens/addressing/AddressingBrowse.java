package com.itk.kdp.web.screens.addressing;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Addressing;

@UiController("kdp_Addressing.browse")
@UiDescriptor("addressing-browse.xml")
@LookupComponent("addressingsTable")
@LoadDataBeforeShow
public class AddressingBrowse extends StandardLookup<Addressing> {
}