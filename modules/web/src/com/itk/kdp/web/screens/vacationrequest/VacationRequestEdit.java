package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.config.ConsultationService;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.VacationRequest;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.*;
import java.util.Formatter;

@UiController("kdp_VacationRequest.edit")
@UiDescriptor("vacation-request-edit.xml")
@EditedEntityContainer("vacationRequestDc")
@LoadDataBeforeShow
public class VacationRequestEdit extends StandardEditor<VacationRequest> {
    @Inject
    private ScreenValidation screenValidation;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Label<String> labelInfo;
    @Inject
    private Messages messages;
    @Inject
    private ConsultationService consultationService;
    @Inject
    private LookupPickerField<Employees> employeeField;
    @Inject
    private TimeSource timeSource;
    @Inject
    private Logger log;
    @Inject
    private DateField<Date> dateByField;
    @Inject
    private Notifications notifications;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        Formatter formatter = new Formatter();
        labelInfo.setValue(formatter.format(messages.getMessage(VacationRequestEdit.class, "message.Info"),
                consultationService.getName(), consultationService.getTelephone()).toString());
        if (Objects.isNull(employeeField.getValue())) {
            notifications.create().withCaption("Вы являетесь сотрудником нескольких организаций.\n Не забудьте оформить отпуск по каждой из них отдельными Заявками на отпуск").show();
            selectEmployee();
        }
    }

    @Subscribe("dateByField")
    public void onDateByFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            ValidationErrors errors = screenValidation.validateCrossFieldRules(this, getEditedEntity());
            if (!errors.isEmpty()) {
                screenValidation.showValidationErrors(this, errors);
                dateByField.setStyleName("v-datefield-error");
            } else {
                dateByField.setStyleName("");
            }
        }
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (PersistenceHelper.isNew(getEditedEntity())) {
            getEditedEntity().setApplicationNumber((int) uniqueNumbersService.getNextNumber(getEditedEntity().getCompany().getCodeRegistration()));
        }
    }

    @Subscribe("dateFromField")
    public void onDateFromFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            this.validateScreen();
        }
    }

    private void selectEmployee() {
        screenBuilders.screen(this)
                .withScreenClass(OrganizationSelectionForm.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    OrganizationSelectionForm organizationSelectionForm = afterCloseEvent.getScreen();
                    if (afterCloseEvent.closedWith(StandardOutcome.SELECT)) {
                        Employees result = organizationSelectionForm.getResult();
                        getEditedEntity().setEmployee(result);
                        getEditedEntity().setDepartment(result.getDepartment());
                        getEditedEntity().setCompany(result.getCompany());
                        getEditedEntity().setPosition(result.getPosition());
                        getEditedEntity().setCoordinator(result.getManager());
                    }
                })
                .build()
                .show();
    }

    @Subscribe("employeeField")
    public void onEmployeeFieldValueChange(HasValue.ValueChangeEvent<Employees> event) {
        if (!Objects.isNull(event.getValue()) && event.isUserOriginated()) {
            getEditedEntity().setDepartment(event.getValue().getDepartment());
            getEditedEntity().setCompany(event.getValue().getCompany());
            getEditedEntity().setPosition(event.getValue().getPosition());
            getEditedEntity().setCoordinator(event.getValue().getManager());
        }
    }
}


