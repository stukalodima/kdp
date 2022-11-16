package com.itk.kdp.web.screens.idea;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.app.core.file.FileDownloadHelper;
import com.haulmont.cuba.gui.components.FileMultiUploadField;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Idea;
import com.itk.kdp.entity.IdeaAttachments;
import com.itk.kdp.service.EmployeeService;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@UiController("kdp_Idea.edit")
@UiDescriptor("idea-edit.xml")
@EditedEntityContainer("ideaDc")
@LoadDataBeforeShow
public class IdeaEdit extends StandardEditor<Idea> {
    @Inject
    private EmployeeService employeeService;
    @Inject
    private UserSession userSession;
    @Inject
    private PickerField<Employees> authorField;
    @Inject
    private PickerField<Employees> managerField;
    @Inject
    private Table<IdeaAttachments> attachmentsTable;
    @Inject
    private FileMultiUploadField multiUploadField;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private Messages messages;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<IdeaAttachments> attachmentsDc;
    @Inject
    private Notifications notifications;
    @Inject
    private DataManager dataManager;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Idea> event) {
        Employees employees = employeeService.getEmployeeByUser(userSession.getUser());
        event.getEntity().setAuthor(employees);
        if (event.getEntity().getAuthor() != null) {
            event.getEntity().setManager(employees.getManager());
            authorField.setEditable(false);
            managerField.setEditable(false);
        }
    }

    @Subscribe
    public void onInit(InitEvent event) {
        FileDownloadHelper.initGeneratedColumn(attachmentsTable, "document");

        multiUploadField.addQueueUploadCompleteListener(queueUploadCompleteEvent -> {
            CommitContext commitContext = new CommitContext();
            for (Map.Entry<UUID, String> entry : multiUploadField.getUploadsMap().entrySet()) {
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileDescriptor fd = fileUploadingAPI.getFileDescriptor(fileId, fileName);
                if (fd != null) {
                    try {
                        fileUploadingAPI.putFileIntoStorage(fileId, fd);
                    } catch (FileStorageException e) {
                        throw new RuntimeException(messages.getMessage(IdeaEdit.class,"ideaBrowse.errorUploadToStorage"), e);
                    }
                    commitContext.addInstanceToCommit(fd);
                    IdeaAttachments ideaAttachments = metadata.create(IdeaAttachments.class);
                    initNewEntity(ideaAttachments, fd);
                    ideaAttachments.setIdea(getEditedEntity());
                    commitContext.addInstanceToCommit(ideaAttachments);
                    attachmentsDc.getMutableItems().add(ideaAttachments);

                }
            }
            dataManager.commit(commitContext);
            multiUploadField.clearUploads();

            FileDownloadHelper.initGeneratedColumn(attachmentsTable, "document");

        });

        multiUploadField.addFileUploadErrorListener(queueFileUploadErrorEvent -> notifications.create()
                .withCaption(messages.getMessage(IdeaEdit.class, "ideaBrowse.errorUpload"))
                .show());
    }

    private void initNewEntity(IdeaAttachments ideaAttachments, FileDescriptor fd) {
        ideaAttachments.setDocument(fd);
        Employees employees = employeeService.getEmployeeByUser(userSession.getUser());
        ideaAttachments.setAuthor(employees);
    }
}