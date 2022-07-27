package com.itk.kdp.web.screens.employees;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.Position;
import com.itk.kdp.web.screens.departments.DepartmentsBrowse;
import com.itk.kdp.web.screens.position.PositionBrowse;

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
    private EntityStates entityStates;
    @Inject
    private Messages messages;
    @Inject
    private CollectionLoader<Position> positionsDl;
    @Inject
    private LookupPickerField<Position> positionField;

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
        updateFromCaption();
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
        if (getEditedEntity().getPhoto() != null) {
            image.setSource(FileDescriptorResource.class).setFileDescriptor(getEditedEntity().getPhoto());
            image.setVisible(true);
        } else {
            image.setVisible(false);
        }
    }

    private void updateFromCaption(){
        if (entityStates.isNew(getEditedEntity())){
            this.getWindow().setCaption(
                    messages.getMessage(EmployeesEdit.class, "Сотрудник организации")
                    + ": "
                    + messages.getMessage(EmployeesEdit.class, "(cоздание)")
            );
        } else {
            this.getWindow().setCaption(
                    messages.getMessage(EmployeesEdit.class, "Сотрудник организации")
                    + ": "
                    + getEditedEntity().getFio()
            );
        }
    }

    @Subscribe("photoField")
    public void onPhotoFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        displayImage();
    }

}