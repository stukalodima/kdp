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
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.file.FileDownloadHelper;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.util.OperationResult;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.entity.*;
import com.itk.kdp.service.EmployeeService;
import com.itk.kdp.web.screens.employees.EmployeesBrowse;
import com.itk.kdp.web.screens.form.StandardEditorITK;
import de.diedavids.cuba.userinbox.entity.Message;
import de.diedavids.cuba.userinbox.entity.SendMessageEntity;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@UiController("kdp_BusinessTrip.edit")
@UiDescriptor("business-trip-edit.xml")
@EditedEntityContainer("businessTripDc")
@LoadDataBeforeShow
public class BusinessTripEdit extends StandardEditorITK<BusinessTrip> {
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
    private TextArea<String> detailsField;
    @Inject
    private LookupPickerField<Purpose> purposeField;
    @Inject
    private CheckBox hotelField;
    @Inject
    private CheckBox visaField;
    @Inject
    private TextArea<String> analyticsField;
    @Inject
    private SuggestionField<String> destinationField;
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
    @Named("bodyTab.mainTab")
    private VBoxLayout mainTab;
    private boolean chooseEmployee;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private EmployeeService employeeService;
    @Inject
    private Button commitBtn;
    @Inject
    private Button closeBtn;
    @Inject
    private TextField<String> startPlaceField;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private MetadataTools metadataTools;

    String SHARE_SUBJECT_KEY = "share.subject";
    String SHARE_TEXT_KEY = "share.text";
    String SHARE_NEW_MESSAGE_SCREEN_ID = "ddcui$send-message";
    @Inject
    private CollectionLoader<Message> messagesDl;
    @Inject
    private CheckBoxGroup<Transport> transportOptionGroup;
    @Inject
    private DataContext dataContext;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
//        updateFormCaption();
        super.onAfterShow(event);
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

    private void updateVisible() {
        sendToApprove.setVisible(Objects.isNull(getEditedEntity().getProcInstance()));
        baseFormSetEditable(Objects.isNull(getEditedEntity().getProcInstance()));
        transportOptionGroup.setEditable(Objects.isNull(getEditedEntity().getProcInstance()));
        startDateField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
        endDateField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
        startPlaceField.setRequired(Objects.isNull(getEditedEntity().getProcInstance()));
        bpmField.setEditable(false);
        if (!Objects.isNull(procTask) && !Objects.isNull(procTask.getProcActor()) && procTask.getProcActor().getUser().equals(userSession.getUser())) {
            if (procTask.getActTaskDefinitionKey().equals("buhApprove")) {
                purposeField.setEditable(true);
                analyticsField.setEditable(true);
                bpmField.setEditable(true);
                destinationField.setRequired(true);
                companyNameField.setRequired(true);
                payCenterField.setRequired(true);
                budgetField.setEditable(false);
                isBudgetField.setEditable(false);
            }
            if (procTask.getActTaskDefinitionKey().equals("logistic")) {
                bpmField.setEditable(true);
                destinationField.setRequired(false);
                companyNameField.setRequired(false);
                payCenterField.setRequired(false);
                destinationField.setEditable(false);
                companyNameField.setEditable(false);
                payCenterField.setEditable(false);
                budgetField.setRequired(true);
                isBudgetField.setRequired(true);
            }
            if (procTask.getActTaskDefinitionKey().equals("correction")) {
                baseFormSetEditable(true);
                transportOptionGroup.setEditable(true);
                detailsField.setEditable(true);
                purposeField.setEditable(true);
                analyticsField.setEditable(true);
                bpmField.setEditable(false);
            }
        }
        if (!Objects.isNull(getEditedEntity().getEmployee())
                && !Objects.isNull(getEditedEntity().getEmployee().getManager())
                && !Objects.isNull(employeeService.getEmployeeByUser(userSession.getUser()))
        ) {
            if (employeeService.getEmployeeByUser(userSession.getUser()).equals(getEditedEntity().getEmployee().getManager())) {
                commitBtn.setVisible(false);
                closeBtn.setVisible(false);
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
        startPlaceField.setEditable(editable);
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
        messagesDl.setParameter("shareable", getEditedEntity());
        messagesDl.load();

        List<Transport> transportList = new ArrayList<>();
        Collection<Transport> transportCollection = new ArrayList<>();
        getEditedEntity().getTransports().forEach(e -> {
            transportList.add(e.getTransport());
            if (Boolean.TRUE.equals(e.getCheckTransport())) {
                transportCollection.add(e.getTransport());
            }
        });
        transportList.sort(Comparator.comparing(Transport::getCaption));
        transportOptionGroup.setOptionsList(transportList);
        transportOptionGroup.setValue(transportCollection);
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
        if (entityStates.isNew(getEditedEntity())) {
            getEditedEntity().setNumber(uniqueNumbersService.getNextNumber("trip"));
            getEditedEntity().setStatus("Проект заявки");
            getEditedEntity().setOnDate(timeSource.currentTimestamp());
        }
        Collection<Transport> transportList = transportOptionGroup.getValue();
        if (transportList != null) {
            getEditedEntity().getTransports().forEach(e -> e.setCheckTransport(transportList.contains(e.getTransport())));
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
            getEditedEntity().setStatus("На погодженні");
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
                        Employees manager = dataManager.reload(getEditedEntity().getEmployee().getApprovalManager(), "employees-for-start-process");
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
                businessTripDl.load();
                getEditedEntity().setProcInstance(procInstance);
                closeWithCommit();
            }
        }
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<BusinessTrip> event) {
        if (entityStates.isNew(event.getEntity())) {
            List<Employees> employees = dataManager.load(Employees.class)
                    .query("select e from kdp_Employees e where e.user = :user")
                    .parameter("user", userSession.getUser())
                    .view("employees-for-create-api")
                    .list();
            if (employees.size() == 1) {
                event.getEntity().setEmployee(employees.get(0));
                event.getEntity().setAuthor(employees.get(0));
            } else if (!employees.isEmpty()) {
                chooseEmployee = true;
            }
            getEditedEntity().setOnDate(timeSource.currentTimestamp());
            event.getEntity().setPayCenter(PayCenterEnum.A);

            List<Transport> transportList = dataManager.load(Transport.class)
                    .query("e.active = TRUE")
                    .view("_base")
                    .list();

            transportList.stream().sorted(Comparator.comparing(Transport::getCaption)).forEach(e -> {
                BusinessTripTransport businessTripTransport = dataManager.create(BusinessTripTransport.class);
                businessTripTransport.setBusinessTrip(event.getEntity());
                businessTripTransport.setTransport(e);
                businessTripTransport = dataContext.merge(businessTripTransport);
                if (event.getEntity().getTransports() == null) {
                    List<BusinessTripTransport> businessTripTransports = new ArrayList<>();
                    businessTripTransports.add(businessTripTransport);
                    event.getEntity().setTransports(businessTripTransports);
                } else {
                    event.getEntity().getTransports().add(businessTripTransport);
                }
            });
        }
    }

    @Subscribe("startDateField")
    public void onStartDateFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (Objects.isNull(getEditedEntity().getOnDate())) {
            getEditedEntity().setOnDate(timeSource.currentTimestamp());
        }
        if (event.isUserOriginated() && !Objects.isNull(event.getValue()) && !Objects.isNull(getEditedEntity().getOnDate())) {
            checkEndDateBeforeStartDate(getEditedEntity().getEndDate());
            if (event.getValue().before(getEditedEntity().getOnDate())) {
                notifications.create().withCaption("Увага!!!")
                        .withDescription("Ви створюєте заявку заднім числом. Дата початку заявки менше за поточну дату!")
                        .show();
            }
        }
    }

    @Subscribe("endDateField")
    public void onEndDateFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated() && !Objects.isNull(event.getValue()) && !Objects.isNull(getEditedEntity().getStartDate())) {
            checkEndDateBeforeStartDate(event.getValue());
        }
    }

    private void checkEndDateBeforeStartDate(Date endDate) {
        if (!Objects.isNull(endDate) && !Objects.isNull(getEditedEntity().getStartDate())) {
            if (endDate.before(getEditedEntity().getStartDate())) {
                notifications.create().withCaption("Увага!!!")
                        .withDescription("Дата початку відрядження не може бути менше дати закінчення!")
                        .show();
            }
        }
    }

    @Subscribe("messagesTable.create")
    public void onMessagesTableCreate(Action.ActionPerformedEvent event) {
        screenBuilders.editor(SendMessageEntity.class, this)
                .withScreenId(SHARE_NEW_MESSAGE_SCREEN_ID)
                .withInitializer(message -> {
                    BusinessTrip shareable = getEditedEntity();
                    String currentUsername = userSessionSource.getUserSession().getCurrentOrSubstitutedUser().getName();
                    String entityCaption = messages.getTools().getEntityCaption(shareable.getMetaClass());
                    String shareableInstanceName = metadataTools.getInstanceName(shareable);
                    message.setSubject(
                            messages.formatMainMessage(
                                    SHARE_SUBJECT_KEY,
                                    currentUsername,
                                    entityCaption,
                                    shareableInstanceName
                            )
                    );
                    message.setText(
                            messages.formatMainMessage(
                                    SHARE_TEXT_KEY,
                                    entityCaption,
                                    shareableInstanceName,
                                    currentUsername
                            )
                    );
                    message.setShareable(shareable);
                })
                .withLaunchMode(OpenMode.DIALOG)
                .show().addAfterCloseListener(e->messagesDl.load());
    }

    @Subscribe
    public void onInit(InitEvent event) {
        List<KeyValueEntity> list = dataManager.loadValues("select o.destination as destination from kdp_BusinessTrip o group by o.destination")
                .properties("destination")
                .list();
        List<String> strings = new ArrayList<>();
        for (KeyValueEntity keyValueEntity : list) {
            strings.add(keyValueEntity.getValue("destination"));
        }
        destinationField.setSearchExecutor((searchString, searchParams) -> {
                    strings.add(searchString);
                    return strings.stream()
                            .filter(str -> StringUtils.containsIgnoreCase(str, searchString))
                            .collect(Collectors.toList());
                }
        );
    }

}