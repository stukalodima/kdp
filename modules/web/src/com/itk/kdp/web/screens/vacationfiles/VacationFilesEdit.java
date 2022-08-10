package com.itk.kdp.web.screens.vacationfiles;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationFiles;
import com.itk.kdp.web.screens.form.StandardEditorITK;

@UiController("kdp_VacationFiles.edit")
@UiDescriptor("vacation-files-edit.xml")
@EditedEntityContainer("vacationFilesDc")
@LoadDataBeforeShow
public class VacationFilesEdit extends StandardEditorITK<VacationFiles> {
}