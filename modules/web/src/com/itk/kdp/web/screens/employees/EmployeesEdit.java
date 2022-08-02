package com.itk.kdp.web.screens.employees;

import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.exception.FileStorageExceptionHandler;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.Position;
import com.itk.kdp.web.screens.departments.DepartmentsBrowse;
import com.itk.kdp.web.screens.form.StandardEditorITK;
import com.itk.kdp.web.screens.position.PositionBrowse;

import javax.inject.Inject;
import java.util.Objects;

@UiController("kdp_Employees.edit")
@UiDescriptor("employees-edit.xml")
@EditedEntityContainer("employeesDc")
@LoadDataBeforeShow
public class EmployeesEdit extends StandardEditorITK<Employees> {
    @Inject
    private CollectionLoader<Departments> departmentsesDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private Image image;
    @Inject
    private LookupPickerField<Departments> departmentField;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<Position> positionsDl;
    @Inject
    private LookupPickerField<Position> positionField;
    @Inject
    private FileStorageService fileStorageService;
    @Inject
    private Messages messages;

    @Subscribe("departmentField.lookup")
    public void onDepartmentFieldLookup(Action.ActionPerformedEvent event) {
        DepartmentsBrowse departmentsBrowse = screenBuilders.lookup(departmentField)
                .withScreenClass(DepartmentsBrowse.class)
                .build();
        departmentsBrowse.setOrganization(getEditedEntity().getCompany());
        departmentsBrowse.show();
    }

    @Subscribe("positionField.lookup")
    public void onPositionFieldLookup(Action.ActionPerformedEvent event) {
        PositionBrowse positionBrowse = screenBuilders.lookup(positionField)
                .withScreenClass(PositionBrowse.class)
                .build();
        positionBrowse.setOrganization(getEditedEntity().getCompany());
        positionBrowse.show();
    }


    @Subscribe("companyField")
    public void onCompanyFieldValueChange(HasValue.ValueChangeEvent<Organizations> event) {
        if (event.isUserOriginated()) {
            Employees editedEntity = getEditedEntity();
            editedEntity.setDepartment(null);
            editedEntity.setPosition(null);

            departmentsesDl.setParameter("organization", event.getValue());
            departmentsesDl.load();

            positionsDl.setParameter("parOrganization", getEditedEntity().getCompany());
            positionsDl.load();
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        super.onAfterShow(event);
        departmentsesDl.setParameter("organization", (Objects.isNull(getEditedEntity().getCompany()) ? dataManager.create(Organizations.class) : getEditedEntity().getCompany()));
        departmentsesDl.load();

        positionsDl.setParameter("parOrganization", getEditedEntity().getCompany());
        positionsDl.load();

        displayImage();
    }

    @Subscribe("photoField")
    public void onPhotoFieldAfterValueClear(FileUploadField.AfterValueClearEvent event) {
        getEditedEntity().setPhoto(null);
        displayImage();
    }

    private void displayImage() {
        try {
            if (getEditedEntity().getPhoto() != null && fileStorageService.fileExists(getEditedEntity().getPhoto())) {
                image.setSource(FileDescriptorResource.class).setFileDescriptor(getEditedEntity().getPhoto());
                image.setVisible(true);
            } else {
                image.setVisible(false);
            }
        } catch (FileStorageException e) {
            throw new RuntimeException(messages.getMainMessage("employees.msg.fileError"), e);
        }
    }

    @Subscribe("photoField")
    public void onPhotoFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        displayImage();
    }

}