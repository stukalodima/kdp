package com.itk.kdp.web.screens.employees;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;

import javax.inject.Inject;
import java.util.Objects;



@UiController("kdp_Employees.edit")
@UiDescriptor("employees-edit.xml")
@EditedEntityContainer("employeesDc")
@LoadDataBeforeShow
public class EmployeesEdit extends StandardEditor<Employees> {
    @Inject
    private CollectionLoader<Departments> departmentsesDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private FileUploadField photoField;
    @Inject
    private Button downloadImageBtn;
    @Inject
    private Button clearImageBtn;
    @Inject
    private InstanceContainer<Employees> employeesDc;
    @Inject
    private Image image;
    @Inject
    private Notifications notifications;

    @Subscribe("companyField")
    public void onCompanyFieldValueChange(HasValue.ValueChangeEvent<Organizations> event) {

        departmentsesDl.setParameter( "organization", event.getValue());
        departmentsesDl.load();
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        departmentsesDl.setParameter("organization", (Objects.isNull(getEditedEntity().getCompany()) ? dataManager.create(Organizations.class) : getEditedEntity().getCompany()));
        departmentsesDl.load();
        photoField.addFileUploadSucceedListener(eventFd -> {
            FileDescriptor fd = photoField.getFileDescriptor();
            try {
                fileUploadingAPI.putFileIntoStorage(photoField.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving file to FileStorage", e);
            }
            getEditedEntity().setPhoto(dataManager.commit(fd));
            displayImage();
        });

        photoField.addFileUploadErrorListener(eventFd ->
                notifications.create().withCaption("Загрузка файла").withDescription("File upload error").show());

        employeesDc.addItemPropertyChangeListener(eventFd -> {
            if ("photoField".equals(eventFd.getProperty()))
                updateImageButtons(eventFd.getValue() != null);
        });

        displayImage();
        updateImageButtons(getEditedEntity().getPhoto() != null);
    }

    public void onDownloadImageBtnClick() {
        if (getEditedEntity().getPhoto() != null)
            exportDisplay.show(getEditedEntity().getPhoto(), ExportFormat.OCTET_STREAM);
    }

    public void onClearImageBtnClick() {
        getEditedEntity().setPhoto(null);
        displayImage();
    }

    private void updateImageButtons(boolean enable) {
        downloadImageBtn.setEnabled(enable);
        clearImageBtn.setEnabled(enable);
    }

    private void displayImage() {
        if (getEditedEntity().getPhoto() != null) {
            image.setSource(FileDescriptorResource.class).setFileDescriptor(getEditedEntity().getPhoto());
            image.setVisible(true);
        } else {
            image.setVisible(false);
        }
    }
}