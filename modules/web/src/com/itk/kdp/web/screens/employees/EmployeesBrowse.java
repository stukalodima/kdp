package com.itk.kdp.web.screens.employees;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.FileDescriptorResource;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;

import javax.inject.Inject;
import java.util.Objects;

@UiController("kdp_Employees.browse")
@UiDescriptor("employees-browse.xml")
@LookupComponent("employeesesTable")
@LoadDataBeforeShow
public class EmployeesBrowse extends StandardLookup<Employees> {

    private Organizations organization;

    @Inject
    private CollectionLoader<Employees> employeesesDl;

    @Inject
    protected GroupTable<Employees> employeesesTable;

    @Inject
    protected UiComponents uiComponents;

    public void setOrganization(Organizations organization){
        this.organization = organization;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (Objects.isNull(organization)) {
            employeesesDl.removeParameter("parOrganization");
        } else {
            employeesesDl.setParameter("parOrganization", organization);
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
        Image photo = smallPhotoImage();
        photo.setSource(FileDescriptorResource.class)
                .setFileDescriptor(imageFile);

        return photo;
    }
    private Image smallPhotoImage() {
        Image photo = uiComponents.create(Image.class);
        photo.setScaleMode(Image.ScaleMode.CONTAIN);
        photo.setHeight("40");
        photo.setWidth("40");
        photo.setStyleName("avatar-icon-small");
        return photo;
    }
}
