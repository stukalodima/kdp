package com.itk.kdp.web.screens.countries;

import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Countries;
import com.itk.kdp.web.screens.employees.EmployeesEdit;

import javax.inject.Inject;

@UiController("kdp_Countries.edit")
@UiDescriptor("countries-edit.xml")
@EditedEntityContainer("countriesDc")
@LoadDataBeforeShow
public class CountriesEdit extends StandardEditor<Countries> {
    @Inject
    private EntityStates entityStates;
    @Inject
    private Messages messages;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        updateFromCaption();
    }


    private void updateFromCaption(){
        if (entityStates.isNew(getEditedEntity())){
            this.getWindow().setCaption(
                    messages.getMessage(CountriesEdit.class, "Страна")
                            + ": "
                            + messages.getMessage(CountriesEdit.class, "(cоздание)")
            );
        } else {
            this.getWindow().setCaption(
                    messages.getMessage(CountriesEdit.class, "Страна")
                            + ": "
                            + getEditedEntity().getName()
            );
        }
    }
}