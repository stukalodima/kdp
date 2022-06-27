package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.core.app.PersistenceManagerService;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.*;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.config.ConsultationService;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.VacationRequest;
import com.itk.kdp.web.screens.employees.EmployeesBrowse;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.Persistence;
import java.awt.image.LookupTable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;

@UiController("kdp_VacationRequest.edit")
@UiDescriptor("vacation-request-edit.xml")
@EditedEntityContainer("vacationRequestDc")
@LoadDataBeforeShow
public class VacationRequestEdit extends StandardEditor<VacationRequest> {
    @Inject
    private ConsultationService consultationService;
    @Inject
    private Label<String> labelInfo;
    @Inject
    private Messages messages;
    @Inject
    private TimeSource timeSource;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private Notifications notifications;
    @Inject
    private DateField<Date> dateFromField;
    @Inject
    private DateField<Date> dateByField;
    @Inject
    private Logger log;
    @Inject
    private LookupPickerField<Employees> employeeField;
    @Inject
    private Dialogs dialogs;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private DataManager dataManager;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private DateField<Date> applicationDateField;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Screens screens;


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        Formatter formatter = new Formatter();
        labelInfo.setValue(formatter.format(messages.getMessage(VacationRequestEdit.class, "message.Info"),
                consultationService.getName(), consultationService.getTelephone()).toString());
        if (!Objects.isNull(employeeField)) {
            selectEmployee();
        }
    }

    @Subscribe("dateByField")
    public void onDateByFieldValueChange(HasValue.ValueChangeEvent<Date> event) {

//        //  Количество дней между датами в миллисекундах
//        long difference = applicationDateField.getValue().getTime() - dateFromField.getValue().getTime();
//        //   Перевод количества дней между датами из миллисекунд в дни
//        int days = (int) (difference / (24 * 60 * 60 * 1000)); // миллисекунды / (24ч * 60мин * 60сек * 1000мс)
//        if (days < 0) {
//            notifications.create()
//                    .withCaption("Дата не может быть меньше текущей")
//                    .show();
//        }

//        //  Количество дней между датами в миллисекундах
//        long difference = dateFromField.getValue().getTime() - dateByField.getValue().getTime();
//        //   Перевод количества дней между датами из миллисекунд в дни
//        int days = (int) (difference / (24 * 60 * 60 * 1000)); // миллисекунды / (24ч * 60мин * 60сек * 1000мс)
//        if (days < 0) {
//            notifications.create()
//                    .withCaption("Дата не может быть меньше даты начала")
//                    .show();
//        }

    }


    //   @Subscribe
    // public void onInitEntity(InitEntityEvent<VacationRequest> event) {
    //event.getEntity().setApplicationDate(timeSource.currentTimestamp());
    //  }


    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (PersistenceHelper.isNew(getEditedEntity())) {
            getEditedEntity().setApplicationNumber((int) uniqueNumbersService.getNextNumber(getEditedEntity().getCompany().getCodeRegistration()));
        }
    }

    @Subscribe("dateFromField")
    public void onDateFromFieldValueChange(HasValue.ValueChangeEvent<Date> event) {

//        LocalDateTime localDateTime = LocalDateTime.ofInstant(applicationDateField.getValue().toInstant(), ZoneId.systemDefault());
//
//      //  Количество дней между датами в миллисекундах
//        long difference = applicationDateField.getValue().getTime() - dateFromField.getValue().getTime();
//      //   Перевод количества дней между датами из миллисекунд в дни
//       int days = (int) (difference / (24 * 60 * 60 * 1000)); // миллисекунды / (24ч * 60мин * 60сек * 1000мс)
//        if (days < 0) {
//            notifications.create()
//                    .withCaption("Дата не может быть меньше текущей")
//                    .show();
//        }
    }

    @Subscribe()
    private void selectEmployee() {


//        if (Objects.isNull(getEditedEntity().getEmployee())) {
//            OrganizationSelectionForm organizationSelectionForm = screens.create(OrganizationSelectionForm.class);
//            organizationSelectionForm.addAfterCloseListener(afterCloseEvent ->
//
//                    )
            screenBuilders.screen(this).withScreenClass(OrganizationSelectionForm.class).withOpenMode(OpenMode.DIALOG).build().show();
        }
    }

