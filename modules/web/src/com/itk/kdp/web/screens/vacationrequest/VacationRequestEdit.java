package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.core.app.PersistenceManagerService;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
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
        if (event.getValue().compareTo(applicationDateField.getValue()) < 0) {
            notifications.create()
                    .withCaption("Дата не может быть меньше текущей")
                    .show();
        }
      int  result = event.getValue().compareTo(dateFromField.getValue());
       // if (result> 0) {
            notifications.create()
                    .withCaption("" + result + " " + dateFromField + " " + dateByField.getValue() + " " + timeSource.currentTimestamp())
                    .show();
       // }
        log.debug(dateFromField.getValue().toString() + "привет");
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
       if(event.getValue().compareTo(timeSource.currentTimestamp())<0) {
           notifications.create()
                   .withCaption("Дата не может быть меньше текущей")
                   .show();
       }
    }

    @Subscribe()
    private void selectEmployee() {

        UserSessionSource userSessionSource = AppBeans.get(UserSessionSource.class);
        DataManager dataManager = AppBeans.get(DataManager.class);


        User user = userSessionSource.getUserSession().getUser();
        if (!Objects.isNull(user.getEmail())) {
            String email = user.getEmail();
            screenBuilders.screen(this).withScreenClass(EmployeesBrowse.class).withOpenMode(OpenMode.DIALOG).build().show();

//            dialogs.createInputDialog(this)
//                    .withCaption("Enter values")
//                    .withParameters(
//                            InputParameter.parameter("employees")
//                                    .withField(() -> {
//                                        return
////                                        GroupTable<Employees> tableEmployees = uiComponents.create(GroupTable.class);
////
////                                        data
////                                        field.setOptionsList(dataManager.load(Employees.class)
////                                                .query("select e from kdp_Employees e where e.workEmail = :workEmail")
////                                                .parameter("workEmail", email)
////                                                .view("employees-view")
////                                                .list());
////                                        field.setCaption("Employees");
////                                        field.setWidthFull();
////                                        return field;
//                                    })
//                    )
//                    .withActions(DialogActions.OK_CANCEL)
//                    .withCloseListener(closeEvent -> {
//                        if (closeEvent.getCloseAction().equals(InputDialog.INPUT_DIALOG_OK_ACTION)) {
//                            String name = closeEvent.getValue("name");
//                            Employees employees = closeEvent.getValue("employees");
//
//                            notifications.create()
//                                    .withCaption("Entered Values")
//                                    .withDescription("<strong>Name:</strong> " + name +
//                                            "<br/><strong>Employees:</strong> " + metadataTools.format(employees))
//                                    .withContentMode(ContentMode.HTML)
//                                    .show();
//                        }
//                    })
//                    .show();
        }
    }


//    @Subscribe()
//    private void selectEmployee() {
//        dialogs.createInputDialog(this)
//               .withCaption("Enter values")
//                .withParameters(
//                       InputParameter.parameter("organizations")
//                                .withField(() -> {
//                                    LookupField<Organizations> field = uiComponents.create(
//                                            LookupField.of(Organizations.class));
//                                    field.setOptionsList(dataManager.load(Organizations.class).list());
//                                    field.setCaption("Organizations");
//                                    field.setWidthFull();
//                                    return field;
//                                })
//                )
//                .withActions(DialogActions.OK_CANCEL)
//                .withCloseListener(closeEvent -> {
//                    if (closeEvent.getCloseAction().equals(InputDialog.INPUT_DIALOG_OK_ACTION)) {
//                        String name = closeEvent.getValue("name");
//                        Organizations organizations = closeEvent.getValue("organizations");
//
//                        notifications.create()
//                                .withCaption("Entered Values")
//                                .withDescription("<strong>Name:</strong> " + name +
//                                        "<br/><strong>Employees:</strong> " + metadataTools.format(organizations))
//                                .withContentMode(ContentMode.HTML)
//                                .show();
//                    }
//                })
//                .show();
//    }

}
