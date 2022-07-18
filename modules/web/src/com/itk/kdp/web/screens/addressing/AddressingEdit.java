package com.itk.kdp.web.screens.addressing;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Addressing;
import com.itk.kdp.entity.Organizations;

import javax.inject.Inject;
import java.util.Objects;

@UiController("kdp_Addressing.edit")
@UiDescriptor("addressing-edit.xml")
@EditedEntityContainer("addressingDc")
@LoadDataBeforeShow
public class AddressingEdit extends StandardEditor<Addressing> {
    @Inject
    private LookupPickerField<Organizations> companyField;

    @Subscribe("useCompanyField")
    public void onUseCompanyFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        companyField.setVisible(Boolean.TRUE.equals(getEditedEntity().getUseCompany()));
        if (event.isUserOriginated()) {
            if (Boolean.FALSE.equals(getEditedEntity().getUseCompany())) {
                getEditedEntity().setCompany(null);
            }
        }
    }
}