package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.bpm.BpmConstants;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.bpm.form.ProcFormDefinition;
import com.haulmont.bpm.gui.action.ClaimProcTaskAction;
import com.haulmont.bpm.gui.action.CompleteProcTaskAction;
import com.haulmont.bpm.gui.action.ProcAction;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.bpm.service.ProcessFormService;
import com.haulmont.bpm.service.ProcessRuntimeService;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.file.FileDownloadHelper;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.util.OperationResult;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.config.ConsultationService;
import com.itk.kdp.entity.*;
import com.itk.kdp.service.EmployeeOrganizationService;
import com.itk.kdp.service.VacationBalanceService;
import com.itk.kdp.web.screens.addressing.AddressingEdit;
import com.itk.kdp.web.screens.employees.EmployeesBrowse;
import de.diedavids.cuba.userinbox.entity.Message;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.*;

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
    private Messages messages;
    @Inject
    private ConsultationService consultationService;
    @Inject
    private LookupPickerField<Employees> employeeField;
    @Inject
    private DateField<Date> dateByField;
    @Inject
    private Notifications notifications;
    @Inject
    private EmployeeOrganizationService employeeOrganizationService;
    @Inject
    private EntityStates entityStates;
    @Inject
    private TextField<Integer> remainingVacationDaysField;
    private boolean chooseEmployee;
    @Inject
    private UserSession userSession;
    @Inject
    private BpmEntitiesService bpmEntitiesService;
    private ProcTask procTask;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private HBoxLayout actionsBox;
    @Inject
    private ProcessFormService processFormService;
    @Named("body.mainTab")
    private VBoxLayout mainTab;
    private final List<CompleteProcTaskAction> completeProcTaskActions = new ArrayList<>();
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Table<VacationFiles> attachmentsTable;
    @Inject
    private InstanceLoader<VacationRequest> vacationRequestDl;
    @Inject
    private CollectionLoader<ProcTask> procTasksDl;
    @Inject
    private CollectionLoader<Message> messagesDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private GlobalConfig globalConfig;
    @Inject
    private ProcessRuntimeService processRuntimeService;
    @Inject
    private Button sendToApprove;
    @Inject
    private VacationBalanceService vacationBalanceService;
    @Inject
    private Dialogs dialogs;
    @Inject
    private MetadataTools metadataTools;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        updateFormCaption();
        Formatter formatter = new Formatter();
        remainingVacationDaysField.setContextHelpText(formatter.format(messages.getMessage(VacationRequestEdit.class, "message.Info"),
                consultationService.getName(), consultationService.getTelephone()).toString());
        initProcAction();
        updateVisible();
        if (chooseEmployee) {
            EmployeesBrowse employeesBrowse = screenBuilders.lookup(employeeField)
                    .withScreenClass(EmployeesBrowse.class)
                    .build();
            employeesBrowse.setUser(userSession.getUser());
            employeesBrowse.show();
        } else {
            fillParameterFromEmployee(getEditedEntity().getEmployee());
        }
    }

    private void fillParameterFromEmployee(Employees employees) {
        if (!Objects.isNull(employees)) {
            getEditedEntity().setCompany(employees.getCompany());
            getEditedEntity().setDepartment(employees.getDepartment());
            getEditedEntity().setPosition(employees.getPosition());
            getEditedEntity().setPhoneNumber(employees.getMobilePhone());
        } else {
            getEditedEntity().setCompany(null);
            getEditedEntity().setDepartment(null);
            getEditedEntity().setPosition(null);
            getEditedEntity().setPhoneNumber(null);
        }
    }

    private void initProcAction() {
        if (!Objects.isNull(getEditedEntity().getProcInstance())) {
            List<ProcTask> procTasks = bpmEntitiesService.findActiveProcTasksForCurrentUser(getEditedEntity().getProcInstance(), BpmConstants.Views.PROC_TASK_COMPLETE);
            procTask = procTasks.isEmpty() ? null : procTasks.get(0);
            if (procTask != null && procTask.getProcActor() != null) {
                initCompleteTaskUI();
            } else if (procTask != null && procTask.getProcActor() == null) {
                initClaimTaskUI();
            }
        }
    }

    private void initClaimTaskUI() {
        Button claimTaskBtn = uiComponents.create(Button.class);
        claimTaskBtn.setWidth("100%");
        claimTaskBtn.setCaption(messages.getMainMessage("form.claimTaskBtn"));

        ProcAction.AfterActionListener afterClaimTaskListener = () -> {
            actionsBox.removeAll();
            initProcAction();
            updateVisible();
        };

        ClaimProcTaskAction claimProcTaskAction = new ClaimProcTaskAction(procTask);
        claimTaskBtn.setAction(claimProcTaskAction);
        claimProcTaskAction.addAfterActionListener(afterClaimTaskListener);
        actionsBox.add(claimTaskBtn);
    }

    protected void initCompleteTaskUI() {
        Map<String, ProcFormDefinition> outcomesWithForms = processFormService.getOutcomesWithForms(procTask);
        if (!outcomesWithForms.isEmpty()) {
            for (Map.Entry<String, ProcFormDefinition> entry : outcomesWithForms.entrySet()) {
                ProcAction.BeforeActionPredicate beforeActionPredicate = () -> {
                    boolean result;
                    if (entry.getKey().equals("Погоджено")) {
                        ValidationErrors validationErrors = screenValidation.validateUiComponents(mainTab);
                        result = validationErrors.isEmpty();
                        if (!result) {
                            screenValidation.showValidationErrors(this, validationErrors);
                        }
                    } else {
//                        detailsField.setEditable(false);
//                        purposeField.setEditable(false);
//                        analyticsField.setEditable(false);
//                        bpmField.setEditable(false);
//                        destinationField.setRequired(false);
//                        companyNameField.setRequired(false);
//                        payCenterField.setRequired(false);
//                        budgetField.setRequired(false);
//                        isBudgetField.setRequired(false);
                        result = true;
                    }
                    return result;
                };
                CompleteProcTaskAction action = new CompleteProcTaskAction(procTask, entry.getKey(), entry.getValue());
                action.setCaption(entry.getKey());
                action.addBeforeActionPredicate(beforeActionPredicate);
                completeProcTaskActions.add(action);
            }
        } else {
            ProcFormDefinition form = processFormService.getDefaultCompleteTaskForm(getEditedEntity().getProcInstance().getProcDefinition());
            CompleteProcTaskAction action = new CompleteProcTaskAction(procTask, BpmConstants.DEFAULT_TASK_OUTCOME, form);
            action.setCaption(messageBundle.getMessage("completeTask"));
            completeProcTaskActions.add(action);
        }

        for (CompleteProcTaskAction completeProcTaskAction : completeProcTaskActions) {
            completeProcTaskAction.addAfterActionListener(this::closeWithCommit);
            Button actionBtn = uiComponents.create(Button.class);
            actionBtn.setWidth("100%");
            actionBtn.setAction(completeProcTaskAction);
            actionsBox.add(actionBtn);
        }
    }

    private void updateVisible() {
        sendToApprove.setVisible(Objects.isNull(getEditedEntity().getProcInstance()));
//        baseFormSetEditable(Objects.isNull(getEditedEntity().getProcInstance()));
//        formTransport.setEditable(Objects.isNull(getEditedEntity().getProcInstance()));
//        startDateField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
//        endDateField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
//        startPlaceField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
//        bpmField.setEditable(false);
//        if (!Objects.isNull(procTask) && !Objects.isNull(procTask.getProcActor()) && procTask.getProcActor().getUser().equals(userSession.getUser())) {
//            if (procTask.getActTaskDefinitionKey().equals("buhApprove")) {
//                purposeField.setEditable(true);
//                analyticsField.setEditable(true);
//                bpmField.setEditable(true);
//                destinationField.setRequired(true);
//                companyNameField.setRequired(true);
//                payCenterField.setRequired(true);
//                budgetField.setEditable(false);
//                isBudgetField.setEditable(false);
//            }
//            if (procTask.getActTaskDefinitionKey().equals("logistic")) {
//                bpmField.setEditable(true);
//                destinationField.setRequired(false);
//                companyNameField.setRequired(false);
//                payCenterField.setRequired(false);
//                destinationField.setEditable(false);
//                companyNameField.setEditable(false);
//                payCenterField.setEditable(false);
//                budgetField.setRequired(true);
//                isBudgetField.setRequired(true);
//            }
//            if (procTask.getActTaskDefinitionKey().equals("correction")) {
//                baseFormSetEditable(true);
//                formTransport.setEditable(true);
//                detailsField.setEditable(true);
//                purposeField.setEditable(true);
//                analyticsField.setEditable(true);
//                bpmField.setEditable(false);
//            }
//        }
//        if (!Objects.isNull(getEditedEntity().getEmployee())
//                && !Objects.isNull(getEditedEntity().getEmployee().getManager())
//                && !Objects.isNull(employeeService.getEmployeeByUser(userSession.getUser()))
//        ) {
//            if (employeeService.getEmployeeByUser(userSession.getUser()).equals(getEditedEntity().getEmployee().getManager())) {
//                commitBtn.setVisible(false);
//                closeBtn.setVisible(false);
//            }
//        }
    }

    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        updateFormCaption();
    }

    private void updateFormCaption() {
        if (entityStates.isNew(getEditedEntity())) {
            this.getWindow().setCaption(
                    messages.getMessage(VacationRequestEdit.class, "vacationRequestEdit.caption")
                            + " "
                            + messages.getMessage(VacationRequestEdit.class, "vacationRequestEdit.newCaption")
            );
        } else {
            DateFormat format = new SimpleDateFormat("dd.MM.yyy");

            this.getWindow().setCaption(
                    messages.getMessage(VacationRequestEdit.class, "vacationRequestEdit.caption")
                            + " "
                            + getEditedEntity().getApplicationNumber()
                            + " "
                            + messages.getMessage(VacationRequestEdit.class, "vacationRequestEdit.dateCaption")
                            + " "
                            + format.format(getEditedEntity().getApplicationDate())
            );
        }
    }

    @Subscribe("dateByField")
    public void onDateByFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            calcDaysVacation();
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
        if (entityStates.isNew(getEditedEntity())) {
            getEditedEntity().setApplicationNumber((int) uniqueNumbersService.getNextNumber("vacation"));
        }
        if (Objects.isNull(getEditedEntity().getStatus())) {
            getEditedEntity().setStatus("Проект заявки");
        }

        if (getEditedEntity().getStatus().equals("Проект заявки")) {
            dialogs.createOptionDialog()
                    .withCaption(messages.getMessage(VacationRequestEdit.class, "vacationRequestEditCommitChanges.notSentForApprovalCaption"))
                    .withMessage(messages.getMessage(VacationRequestEdit.class, "vacationRequestEditCommitChanges.notSentForApprovalMessage"))
                    .withActions(
                            new DialogAction(DialogAction.Type.YES).withHandler(e -> {
                                // Продовжуэмо збереження
                                event.resume();
                            }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
            // не зберігаємо
            event.preventCommit();
        }
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        FileDownloadHelper.initGeneratedColumn(attachmentsTable, "document");
        vacationRequestDl.load();
        if (Objects.isNull(getEditedEntity().getProcInstance())) {
            procTasksDl.setParameter("procInstance", null);
        } else {
            procTasksDl.setParameter("procInstance", getEditedEntity().getProcInstance().getId());
        }
        procTasksDl.load();
        messagesDl.setParameter("shareable", getEditedEntity());
        messagesDl.load();
    }

    @Subscribe("dateFromField")
    public void onDateFromFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            calcDaysVacation();
            this.validateScreen();
        }
    }

    private void calcDaysVacation() {
        if (!Objects.isNull(getEditedEntity().getDateFrom()) && !(Objects.isNull(getEditedEntity().getDateBy()))) {
            long milliseconds = getEditedEntity().getDateBy().getTime() - getEditedEntity().getDateFrom().getTime();
            getEditedEntity().setVacationDays(((int) (milliseconds / (24 * 60 * 60 * 1000)) )+ 1);
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
            getVacationBalance();
            getEditedEntity().setDepartment(event.getValue().getDepartment());
            getEditedEntity().setCompany(event.getValue().getCompany());
            getEditedEntity().setPosition(event.getValue().getPosition());
            getEditedEntity().setCoordinator(event.getValue().getManager());
        }
    }

    @Subscribe("vacationTypeField")
    public void onVacationTypeFieldValueChange(HasValue.ValueChangeEvent<VacationType> event) {
        if (event.isUserOriginated()) {
            getVacationBalance();
        }
    }



    private void getVacationBalance() {
        if (!Objects.isNull(getEditedEntity().getEmployee()) && !Objects.isNull(getEditedEntity().getVacationType())) {
            VacationBalance vacationBalance = vacationBalanceService.getVacationBalanceByEmployeeAndVacationType(
                    getEditedEntity().getEmployee(), getEditedEntity().getVacationType()
            );
            if (!Objects.isNull(vacationBalance) && !Objects.isNull(vacationBalance.getDays())) {
                getEditedEntity().setRemainingVacationDays(vacationBalance.getDays());
            } else {
                getEditedEntity().setRemainingVacationDays(0);
            }
        }
    }

    @Subscribe("sendToApprove")
    public void onSendToApproveClick(Button.ClickEvent event) {
        if (Objects.isNull(getEditedEntity().getProcInstance())) {
            if(getEditedEntity().getEmployee().getVacationManager() == null) {
                notifications.create()
                        .withCaption(messages.getMessage(VacationRequestEdit.class, "message.startProcess.error"))
                        .withType(Notifications.NotificationType.ERROR)
                        .show();
                return;
            }
            getEditedEntity().setStatus("На погодженні");
            if (commitChanges().getStatus() == OperationResult.Status.SUCCESS) {

                List<Addressing> addressingList = dataManager.load(Addressing.class)
                        .query("select e from kdp_Addressing e where e.procEntity = :procEntity")
                        .parameter("procEntity", ProcEntityEnum.VACATION_REQUEST.getId())
                        .view("addressing-all-property")
                        .list();

                if (addressingList.isEmpty()) {
                    return;
                }

                Addressing addressing = addressingList.get(0);

                List<AddressingDetail> listRoles = addressing.getAddressingDetail();

                BpmEntitiesService.ProcInstanceDetails procInstanceDetails = new BpmEntitiesService.ProcInstanceDetails(addressing.getProcDefinition().getCode());
                listRoles.forEach(e -> {
                    if (Boolean.TRUE.equals(e.getIsInitial())) {
                        procInstanceDetails.addProcActor(e.getProcRole(), userSession.getUser());
                    } else if (Boolean.TRUE.equals(e.getIsManager())) {
                        Employees manager = dataManager.reload(getEditedEntity().getEmployee().getVacationManager(), "employees-for-start-process");
                        procInstanceDetails.addProcActor(e.getProcRole(), manager.getUser());
                    } else if (Boolean.TRUE.equals(e.getAuto())) {
                        procInstanceDetails.addProcActor(
                                e.getProcRole(),
                                dataManager.getReference(
                                        User.class,
                                        Objects.requireNonNull(globalConfig.getAnonymousSessionId())
                                )
                        );
                    } else {
                        procInstanceDetails.addProcActor(e.getProcRole(), e.getUser());
                    }
                });
                procInstanceDetails.setEntity(getEditedEntity());

                ProcInstance procInstance = bpmEntitiesService.createProcInstance(procInstanceDetails);

                HashMap<String, Object> processVariables = new HashMap<>();
                processRuntimeService.startProcess(procInstance, "Process started programmatically", processVariables);
                notifications.create()
                        .withCaption("Відправлено по маршруту")
                        .withType(Notifications.NotificationType.HUMANIZED)
                        .show();
                vacationRequestDl.load();
                getEditedEntity().setProcInstance(procInstance);
                closeWithCommit();
            }
        }
    }
    public Component generateNameAllProcActors(ProcTask entity) {
        String nameAllUsers;

        Label<String> amountField = uiComponents.create(Label.TYPE_STRING);

        if (Objects.isNull(entity.getProcActor())) {

            entity = dataManager.reload(entity, ViewBuilder.of(ProcTask.class)
                    .addAll("candidateUsers", "candidateUsers.name", "candidateUsers.login")
                    .build());

            nameAllUsers = "";
            if (entity.getCandidateUsers() != null) {
                Set<User> canditateUser = entity.getCandidateUsers();

                int n = 0;
                for (User cUser : canditateUser) {
                    n++;
                    nameAllUsers = nameAllUsers + metadataTools.getInstanceName(cUser) + (canditateUser.size() == n ? "" : ",\n");
                }
            }
            amountField.setValue(nameAllUsers);

        } else {
          amountField.setValue(metadataTools.getInstanceName(entity.getProcActor()));
        }

        return amountField;
    }
}


