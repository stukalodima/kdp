package com.itk.kdp.web.screens.reconciliationstatuses;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.ReconciliationStatuses;

@UiController("kdp_ReconciliationStatuses.browse")
@UiDescriptor("reconciliation-statuses-browse.xml")
@LookupComponent("reconciliationStatusesesTable")
@LoadDataBeforeShow
public class ReconciliationStatusesBrowse extends StandardLookup<ReconciliationStatuses> {
}