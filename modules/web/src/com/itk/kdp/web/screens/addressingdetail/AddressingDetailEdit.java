package com.itk.kdp.web.screens.addressingdetail;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.entity.AddressingDetail;

import javax.inject.Inject;

@UiController("kdp_AddressingDetail.edit")
@UiDescriptor("addressing-detail-edit.xml")
@EditedEntityContainer("addressingDetailDc")
@LoadDataBeforeShow
public class AddressingDetailEdit extends StandardEditor<AddressingDetail> {

    @Inject
    private PickerField<User> userField;

    @Subscribe("isInitialField")
    public void onIsInitialFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.isUserOriginated()) {
            if (Boolean.TRUE.equals(event.getValue())) {
                getEditedEntity().setAuto(Boolean.FALSE);
                getEditedEntity().setIsManager(Boolean.FALSE);
                userField.setEditable(Boolean.FALSE);
                userField.setValue(null);
            } else {
                userField.setEditable(Boolean.TRUE);
            }
        }
    }

    @Subscribe("isManagerField")
    public void onIsManagerFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.isUserOriginated()) {
            if (Boolean.TRUE.equals(event.getValue())) {
                getEditedEntity().setAuto(Boolean.FALSE);
                getEditedEntity().setIsInitial(Boolean.FALSE);
                userField.setEditable(Boolean.FALSE);
                userField.setValue(null);
            } else {
                userField.setEditable(Boolean.TRUE);
            }
        }
    }

    @Subscribe("autoField")
    public void onAutoFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.isUserOriginated()) {
            if (Boolean.TRUE.equals(event.getValue())) {
                getEditedEntity().setIsInitial(Boolean.FALSE);
                getEditedEntity().setIsInitial(Boolean.FALSE);
                userField.setEditable(Boolean.FALSE);
                userField.setValue(null);
            } else {
                userField.setEditable(Boolean.TRUE);
            }
        }
    }
}