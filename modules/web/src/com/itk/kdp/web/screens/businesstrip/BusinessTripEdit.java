package com.itk.kdp.web.screens.businesstrip;

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
import com.itk.kdp.entity.*;
import com.itk.kdp.web.screens.employees.EmployeesBrowse;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@UiController("kdp_BusinessTrip.edit")
@UiDescriptor("business-trip-edit.xml")
@EditedEntityContainer("businessTripDc")
@LoadDataBeforeShow
public class BusinessTripEdit extends StandardEditor<BusinessTrip> {
    protected ProcTask procTask;
    @Inject
    private InstanceLoader<BusinessTrip> businessTripDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private BpmEntitiesService bpmEntitiesService;
    @Inject
    private ProcessRuntimeService processRuntimeService;
    @Inject
    private Notifications notifications;
    @Inject
    private ProcessFormService processFormService;
    private final List<CompleteProcTaskAction> completeProcTaskActions = new ArrayList<>();
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private HBoxLayout actionsBox;
    @Inject
    private Button sendToApprove;
    @Inject
    private TimeSource timeSource;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private Form formTransport;
    @Inject
    private UserSession userSession;
    @Inject
    private GlobalConfig globalConfig;
    @Inject
    private Form bpmField;
    @Inject
    private DateField<Date> startDateField;
    @Inject
    private DateField<Date> endDateField;
    @Inject
    private Table<BusinessTripFiles> attachmentsTable;
    @Inject
    private CollectionLoader<ProcTask> procTasksDl;
    @Inject
    private LookupPickerField<Employees> employeeField;
    @Inject
    private TextField<String> detailsField;
    @Inject
    private LookupPickerField<Purpose> purposeField;
    @Inject
    private CheckBox hotelField;
    @Inject
    private CheckBox visaField;
    @Inject
    private TextField<String> analyticsField;
    @Inject
    private TextField<String> destinationField;
    @Inject
    private TextField<String> companyNameField;
    @Inject
    private LookupField<PayCenterEnum> payCenterField;
    @Inject
    private TextField<String> budgetField;
    @Inject
    private TextField<String> isBudgetField;
    @Inject
    private EntityStates entityStates;
    @Inject
    private Messages messages;
    @Inject
    private ScreenValidation screenValidation;
    @Named("body.mainTab")
    private VBoxLayout mainTab;
    private boolean chooseEmployee;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private GroupBoxLayout bpmGroup;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        updateFormCaption();
        initProcAction();
        updateVisible();
        if (chooseEmployee) {
            EmployeesBrowse employeesBrowse = screenBuilders.lookup(employeeField)
                    .withScreenClass(EmployeesBrowse.class)
                    .build();
            employeesBrowse.setUser(userSession.getUser());
            employeesBrowse.show();
        }
    }

    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        updateFormCaption();
    }

    private void updateFormCaption() {
        if (entityStates.isNew(getEditedEntity())) {
            this.getWindow().setCaption(
                    messages.getMessage(BusinessTripEdit.class, "businessTripEdit.caption")
                            + " "
                            + messages.getMessage(BusinessTripEdit.class, "businessTripEdit.newCaption")
            );
        } else {
            DateFormat format = new SimpleDateFormat("dd.MM.yyy");

            this.getWindow().setCaption(
                    messages.getMessage(BusinessTripEdit.class, "businessTripEdit.caption")
                            + " "
                            + getEditedEntity().getNumber()
                            + " "
                            + messages.getMessage(BusinessTripEdit.class, "businessTripEdit.dateCaption")
                            + " "
                            + format.format(getEditedEntity().getOnDate())
            );
        }
    }

    private void updateVisible() {
        sendToApprove.setVisible(Objects.isNull(getEditedEntity().getProcInstance()));
        baseFormSetEditable(Objects.isNull(getEditedEntity().getProcInstance()));
        formTransport.setEditable(Objects.isNull(getEditedEntity().getProcInstance()));
        startDateField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
        endDateField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
//        bpmGroup.is
        bpmField.setEditable(false);
        if (!Objects.isNull(procTask) && !Objects.isNull(procTask.getProcActor()) && procTask.getProcActor().getUser().equals(userSession.getUser())) {
            if (procTask.getActTaskDefinitionKey().equals("buhApprove")) {
                detailsField.setEditable(true);
                purposeField.setEditable(true);
                analyticsField.setEditable(true);
                bpmField.setEditable(true);
                destinationField.setRequired(true);
                companyNameField.setRequired(true);
                payCenterField.setRequired(true);
            }
            if (procTask.getActTaskDefinitionKey().equals("logistic")) {
                bpmField.setEditable(true);
                budgetField.setRequired(true);
                isBudgetField.setRequired(true);
            }
            if (procTask.getActTaskDefinitionKey().equals("correction")) {
                baseFormSetEditable(true);
                formTransport.setEditable(true);
                detailsField.setEditable(true);
                purposeField.setEditable(true);
                analyticsField.setEditable(true);
                bpmField.setEditable(false);
            }
        }
    }

    private void baseFormSetEditable(boolean editable) {
        employeeField.setEditable(editable);
        startDateField.setEditable(editable);
        endDateField.setEditable(editable);
        detailsField.setEditable(editable);
        purposeField.setEditable(editable);
        hotelField.setEditable(editable);
        visaField.setEditable(editable);
        analyticsField.setEditable(editable);
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

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        FileDownloadHelper.initGeneratedColumn(attachmentsTable, "document");
        businessTripDl.load();
        if (Objects.isNull(getEditedEntity().getProcInstance())) {
            procTasksDl.setParameter("procInstance", null);
        } else {
            procTasksDl.setParameter("procInstance", getEditedEntity().getProcInstance().getId());
        }
        procTasksDl.load();
    }

    private void initClaimTaskUI() {
        Button claimTaskBtn = uiComponents.create(Button.class);
        claimTaskBtn.setWidth("100%");

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
                    if (entry.getKey().equals("??????????????????????")) {
                        ValidationErrors validationErrors = screenValidation.validateUiComponents(mainTab);
                        result = validationErrors.isEmpty();
                        if (!result) {
                            screenValidation.showValidationErrors(this, validationErrors);
                        }
                    } else {
                        detailsField.setEditable(false);
                        purposeField.setEditable(false);
                        analyticsField.setEditable(false);
                        bpmField.setEditable(false);
                        destinationField.setRequired(false);
                        companyNameField.setRequired(false);
                        payCenterField.setRequired(false);
                        budgetField.setRequired(false);
                        isBudgetField.setRequired(false);
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

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (Objects.isNull(getEditedEntity().getOnDate())) {
            getEditedEntity().setOnDate(timeSource.currentTimestamp());
            getEditedEntity().setNumber(uniqueNumbersService.getNextNumber("trip"));
            getEditedEntity().setStatus("???????????? ????????????");
        }
    }

    @Subscribe("employeeField")
    public void onEmployeeFieldValueChange(HasValue.ValueChangeEvent<Employees> event) {
        if (event.isUserOriginated()) {
            fillParameterFromEmployee(event.getValue());
        }
    }

    private void fillParameterFromEmployee(Employees employees) {
        if (!Objects.isNull(employees)) {
            getEditedEntity().setOrganization(employees.getCompany());
            getEditedEntity().setDepartment(employees.getDepartment());
            getEditedEntity().setPosition(employees.getPosition());
            getEditedEntity().setPhoneNumber(employees.getMobilePhone());
        } else {
            getEditedEntity().setOrganization(null);
            getEditedEntity().setDepartment(null);
            getEditedEntity().setPosition(null);
            getEditedEntity().setPhoneNumber(null);
        }
    }

    @Subscribe("sendToApprove")
    public void onSendToApproveClick(Button.ClickEvent event) {
        if (Objects.isNull(getEditedEntity().getProcInstance())) {
            getEditedEntity().setStatus("???? ????????????????????????");
            if (commitChanges().getStatus() == OperationResult.Status.SUCCESS) {

                List<Addressing> addressingList = dataManager.load(Addressing.class)
                        .query("select e from kdp_Addressing e where e.procEntity = :procEntity")
                        .parameter("procEntity", ProcEntityEnum.BUSINESS_TRIP.getId())
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
                        Employees manager = dataManager.reload(getEditedEntity().getEmployee().getManager(), "employees-edit");
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
                        .withCaption("???????????????????? ???? ????????????????")
                        .withType(Notifications.NotificationType.HUMANIZED)
                        .show();
                businessTripDl.load();
                getEditedEntity().setProcInstance(procInstance);
                closeWithCommit();
            }
        }
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<BusinessTrip> event) {
        List<Employees> employees = dataManager.load(Employees.class)
                .query("select e from kdp_Employees e where e.user = :user")
                .parameter("user", userSession.getUser())
                .view("employees-view")
                .list();
        if (employees.size() == 1) {
            event.getEntity().setEmployee(employees.get(0));
            event.getEntity().setAuthor(employees.get(0));
        } else if (!employees.isEmpty()) {
            chooseEmployee = true;
        }
    }
}