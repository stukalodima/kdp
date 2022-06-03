package com.itk.kdp.web.screens.organizations;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Organizations;

@UiController("kdp_Organizations.edit")
@UiDescriptor("organizations-edit.xml")
@EditedEntityContainer("organizationsDc")
@LoadDataBeforeShow
public class OrganizationsEdit extends StandardEditor<Organizations> {
}