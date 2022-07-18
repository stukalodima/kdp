package com.itk.kdp.web.screens.businesstripfiles;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.BusinessTripFiles;

@UiController("kdp_BusinessTripFiles.edit")
@UiDescriptor("business-trip-files-edit.xml")
@EditedEntityContainer("businessTripFilesDc")
@LoadDataBeforeShow
public class BusinessTripFilesEdit extends StandardEditor<BusinessTripFiles> {
}