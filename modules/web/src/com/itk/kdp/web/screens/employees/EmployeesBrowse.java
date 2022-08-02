package com.itk.kdp.web.screens.employees;

import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.service.EmployeeService;
import com.itk.kdp.web.screens.form.DialogsITK;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Objects;

@UiController("kdp_Employees.browse")
@UiDescriptor("employees-browse.xml")
@LookupComponent("employeesesTable")
@LoadDataBeforeShow
public class EmployeesBrowse extends StandardLookup<Employees> {

    private Organizations organization;

    private User user;

    @Inject
    private CollectionLoader<Employees> employeesesDl;

    @Inject
    protected GroupTable<Employees> employeesesTable;

    @Inject
    protected UiComponents uiComponents;
    @Inject
    private EmployeeService employeeService;
    @Inject
    private Dialogs dialogs;
    @Inject
    private FileStorageService fileStorageService;
    @Inject
    private Messages messages;

    public void setOrganization(Organizations organization) {
        this.organization = organization;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (Objects.isNull(organization)) {
            employeesesDl.removeParameter("parOrganization");
        } else {
            employeesesDl.setParameter("parOrganization", organization);
        }
        if (Objects.isNull(user)) {
            employeesesDl.removeParameter("user");
        } else {
            employeesesDl.setParameter("user", getUser());
        }
        employeesesDl.load();
    }

    @Subscribe
    protected void onInit(InitEvent event) {
        employeesesTable.addGeneratedColumn("photo", this::renderAvatarImageComponent);
    }

    private Component renderAvatarImageComponent(Employees employees) {
        FileDescriptor imageFile = employees.getPhoto();
        if (imageFile == null) {
            return null;
        }
        try {
            if (fileStorageService.fileExists(imageFile)) {
                Image photo = smallPhotoImage();
                photo.setSource(FileDescriptorResource.class)
                        .setFileDescriptor(imageFile);

                return photo;
            } else {
                return null;
            }
        } catch (FileStorageException e) {
            throw new RuntimeException(messages.getMainMessage("employees.msg.fileError"),e);
        }
    }

    private Image smallPhotoImage() {
        Image photo = uiComponents.create(Image.class);
        photo.setScaleMode(Image.ScaleMode.CONTAIN);
        photo.setHeight("40");
        photo.setWidth("40");
        photo.setStyleName("avatar-icon-small");
        return photo;
    }

    @Subscribe("employeesesTable.fillFromExternal")
    public void onEmployeesesTableFillFromExternal(Action.ActionPerformedEvent event) {
        try {
            employeeService.getEmployeeListFromExternal();
            DialogsITK.getDialogForImportSuccess(dialogs, EmployeesBrowse.class).show();
        } catch (IOException e) {
            DialogsITK.getDialogForImportError(dialogs, e, EmployeesBrowse.class).show();
        }
        employeesesDl.load();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
