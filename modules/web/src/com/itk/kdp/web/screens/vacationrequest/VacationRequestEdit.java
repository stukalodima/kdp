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
import com.itk.kdp.entity.AddressingDetail;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.VacationRequest;
import com.itk.kdp.service.EmployeeOrganizationService;
import org.slf4j.Logger;
import com.itk.kdp.entity.*;

import javax.inject.Inject;
import java.util.*;
import java.util.Formatter;

@UiController("kdp_VacationRequest.edit")
@UiDescriptor("vacation-request-edit.xml")
@EditedEntityContainer("vacationRequestDc")
@LoadDataBeforeShow
public class VacationRequestEdit extends StandardEditor<VacationRequest> {
    private static final String PROCESS_CODE = "Vacation";

    public static final String QUERY_STRING_ROLES_BY_BUSINESS =
            "select e from kdp_AddressingDetail e " +
                    "where " +
                    "e.addressing.procDefinition.code = :procDefinition ";
    @Inject
    private InstanceLoader<VacationRequest> vacationRequestDl;
    @Inject
    private ScreenValidation screenValidation;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private ScreenBuilders screenBuilders;
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
    @Inject
    private EmployeeOrganizationService employeeOrganizationService;
    @Inject
    private Table<VacationRequestFiles> attachmentsTable;
    @Inject
    private CollectionLoader<ProcTask> procTasksDl;
    @Inject
    private BpmEntitiesService bpmEntitiesService;
    @Inject
    private ProcessRuntimeService processRuntimeService;
    @Inject
    private ProcessFormService processFormService;
    private final List<CompleteProcTaskAction> completeProcTaskActions = new ArrayList<>();
    protected ProcTask procTask;
    @Inject
    private Button sendToApprove;
    @Inject
    private Form bpmField;
    private UiComponents uiComponents;
    @Inject
    private HBoxLayout actionsBox;
    @Inject
    private UserSession userSession;
    @Inject
    private GlobalConfig globalConfig;
    @Inject
    private DataManager dataManager;
    @Inject
    private MessageBundle messageBundle;

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
    }
    @Subscribe
    public void onAfterShow(AfterShowEvent event) {

        initProcAction();
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

        ProcAction.AfterActionListener afterClaimTaskListener = () -> {
            actionsBox.removeAll();
            initProcAction();
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
                action.setCaption(entry.getKey());
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
                vacationRequestDl.load();
                getEditedEntity().setProcInstance(procInstance);
                closeWithCommit();
            }
        }
    }

}


