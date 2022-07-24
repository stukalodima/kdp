package com.itk.kdp.web.screens.employees;

import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.exception.FileStorageExceptionHandler;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.web.screens.departments.DepartmentsBrowse;

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
    private Image image;
    @Inject
    private LookupPickerField<Departments> departmentField;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private FileStorageExceptionHandler fileStorageExceptionHandler;
    @Inject
    private FileStorageService fileStorageService;

    @Subscribe("departmentField.lookup")
    public void onDepartmentFieldLookup(Action.ActionPerformedEvent event) {
        DepartmentsBrowse departmentsBrowse = screenBuilders.lookup(departmentField)
                .withScreenClass(DepartmentsBrowse.class)
                .build();
        departmentsBrowse.setOrganization(getEditedEntity().getCompany());
        departmentsBrowse.show();
    }


    @Subscribe("companyField")
    public void onCompanyFieldValueChange(HasValue.ValueChangeEvent<Organizations> event) {
        departmentsesDl.setParameter("organization", event.getValue());
        departmentsesDl.load();
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) throws FileStorageException {
        departmentsesDl.setParameter("organization", (Objects.isNull(getEditedEntity().getCompany()) ? dataManager.create(Organizations.class) : getEditedEntity().getCompany()));
        departmentsesDl.load();

        displayImage();
    }

    @Subscribe("photoField")
    public void onPhotoFieldAfterValueClear(FileUploadField.AfterValueClearEvent event) throws FileStorageException {
        getEditedEntity().setPhoto(null);
        displayImage();
    }

    private void displayImage() throws FileStorageException {
        if (getEditedEntity().getPhoto() != null && fileStorageService.fileExists(getEditedEntity().getPhoto())) {
            image.setSource(FileDescriptorResource.class).setFileDescriptor(getEditedEntity().getPhoto());
            image.setVisible(true);
        } else {
            image.setVisible(false);
        }
    }

    @Subscribe("photoField")
    public void onPhotoFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) throws FileStorageException {
        displayImage();
    }

}