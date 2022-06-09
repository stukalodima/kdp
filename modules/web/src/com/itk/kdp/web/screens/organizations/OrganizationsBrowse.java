package com.itk.kdp.web.screens.organizations;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;

@UiController("kdp_Organizations.browse")
@UiDescriptor("organizations-browse.xml")
@LookupComponent("organizationsesTable")
@LoadDataBeforeShow
public class OrganizationsBrowse extends StandardLookup<Organizations> {
}