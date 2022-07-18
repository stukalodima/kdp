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
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.Notifications;
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

import javax.inject.Inject;
import java.util.*;

@UiController("kdp_BusinessTrip.edit")
@UiDescriptor("business-trip-edit.xml")
@EditedEntityContainer("businessTripDc")
@LoadDataBeforeShow
public class BusinessTripEdit extends StandardEditor<BusinessTrip> {
    private static final String PROCESS_CODE = "Trip";

    public static final String QUERY_STRING_ROLES_BY_BUSINESS =
            "select e from kdp_AddressingDetail e " +
                    "where " +
                    "e.addressing.procDefinition.code = :procDefinition ";
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
    private LookupPickerField<Organizations> organizationField;
    @Inject
    private LookupPickerField<Position> positionField;
    @Inject
    private LookupPickerField<Departments> departmentField;
    @Inject
    private TextField<String> phoneNumberField;
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

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        initProcAction();
        updateVisible();
    }

    private void updateVisible() {
        sendToApprove.setVisible(Objects.isNull(getEditedEntity().getProcInstance()));
        baseFormSetEditable(Objects.isNull(getEditedEntity().getProcInstance()));
        formTransport.setEditable(Objects.isNull(getEditedEntity().getProcInstance()));
        startDateField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
        endDateField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
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
        }
    }

    private void baseFormSetEditable(boolean editable) {
        employeeField.setEditable(editable);
        organizationField.setEditable(editable);
        positionField.setEditable(editable);
        departmentField.setEditable(editable);
        phoneNumberField.setEditable(editable);
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
                CompleteProcTaskAction action = new CompleteProcTaskAction(procTask, entry.getKey(), entry.getValue());
                action.setCaption(
//                        processMessagesService.getMessage(
//                                procTask.getProcInstance().getProcDefinition().getActId(),
//                                procTask.getActTaskDefinitionKey() + "." +
                        entry.getKey()
//                        )
                );
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
            getEditedEntity().setStatus("Проект заявки");
        }
    }

    @Subscribe("employeeField")
    public void onEmployeeFieldValueChange(HasValue.ValueChangeEvent<Employees> event) {
        if (event.isUserOriginated()) {
            if (!Objects.isNull(event.getValue())) {
                getEditedEntity().setOrganization(event.getValue().getCompany());
                getEditedEntity().setDepartment(event.getValue().getDepartment());
                getEditedEntity().setPosition(event.getValue().getPosition());
                getEditedEntity().setPhoneNumber(event.getValue().getMobilePhone());
            } else {
                getEditedEntity().setOrganization(null);
                getEditedEntity().setDepartment(null);
                getEditedEntity().setPosition(null);
                getEditedEntity().setPhoneNumber(null);
            }
        }
    }

    @Subscribe("sendToApprove")
    public void onSendToApproveClick(Button.ClickEvent event) {
        if (Objects.isNull(getEditedEntity().getProcInstance())) {
            getEditedEntity().setStatus("На согласовании");
            if (commitChanges().getStatus() == OperationResult.Status.SUCCESS) {
                List<AddressingDetail> listRoles = dataManager.load(AddressingDetail.class)
                        .query(QUERY_STRING_ROLES_BY_BUSINESS)
                        .parameter("procDefinition", PROCESS_CODE)
                        .view("addressingDetail-all-property")
                        .list();

                BpmEntitiesService.ProcInstanceDetails procInstanceDetails = new BpmEntitiesService.ProcInstanceDetails(PROCESS_CODE);
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
                        .withCaption("Отправлено по маршруту")
                        .withType(Notifications.NotificationType.HUMANIZED)
                        .show();
                businessTripDl.load();
                getEditedEntity().setProcInstance(procInstance);
                closeWithCommit();
            }
        }
    }
}