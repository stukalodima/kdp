package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.VacationRequest;

import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.bpm.gui.procactionsfragment.ProcActionsFragment;
import com.haulmont.cuba.gui.app.core.file.FileDownloadHelper;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;


import javax.inject.Inject;
import com.haulmont.bpm.entity.ProcAttachment;
@UiController("kdp_VacationRequest.edit")
@UiDescriptor("vacation-request-edit.xml")
@EditedEntityContainer("vacationRequestDc")
@LoadDataBeforeShow
public class VacationRequestEdit extends StandardEditor<VacationRequest> {
    private static final String PROCESS_CODE = "vacationRequestApproval";

    @Inject
    private CollectionLoader<ProcAttachment> procAttachmentsDl;

    @Inject
    private InstanceContainer<VacationRequest> vacationRequestDc;

    @Inject
    protected ProcActionsFragment procActionsFragment;

    @Inject
    private Table<ProcAttachment> attachmentsTable;

    @Inject
    private InstanceLoader<VacationRequest> vacationRequestDl;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        vacationRequestDl.load();
        procAttachmentsDl.setParameter("entityId",vacationRequestDc.getItem().getId());
        procAttachmentsDl.load();
        procActionsFragment.initializer()
                .standard()
                .init(PROCESS_CODE, getEditedEntity());

        FileDownloadHelper.initGeneratedColumn(attachmentsTable, "file");
    }
}
