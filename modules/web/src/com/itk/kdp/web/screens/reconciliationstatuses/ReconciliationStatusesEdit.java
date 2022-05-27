package com.itk.kdp.web.screens.reconciliationstatuses;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.ReconciliationStatuses;

@UiController("kdp_ReconciliationStatuses.edit")
@UiDescriptor("reconciliation-statuses-edit.xml")
@EditedEntityContainer("reconciliationStatusesDc")
@LoadDataBeforeShow
public class ReconciliationStatusesEdit extends StandardEditor<ReconciliationStatuses> {
}