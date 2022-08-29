package com.itk.kdp.web.screens.form;

import com.haulmont.bpm.BpmConstants;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.bpm.form.ProcFormDefinition;
import com.haulmont.bpm.gui.action.CancelProcessAction;
import com.haulmont.bpm.gui.action.ClaimProcTaskAction;
import com.haulmont.bpm.gui.action.CompleteProcTaskAction;
import com.haulmont.bpm.gui.action.ProcAction;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.bpm.service.ProcessFormService;
import com.haulmont.bpm.service.ProcessMessagesService;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.base.itk.StandardEntityITK;
import com.itk.kdp.entity.BusinessTrip;
import com.itk.kdp.entity.helper.MessageHelperITK;
import de.diedavids.cuba.userinbox.entity.Message;
import de.diedavids.cuba.userinbox.entity.SendMessageEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class StandardEditorITK<O extends StandardEntity> extends StandardEditor<O> {
    protected ProcTask procTask;
    protected Boolean isProcess;
    protected ProcInstance procInstanceEntity;
    protected HBoxLayout actionsBoxForProcessBtn;
    protected List<User> userListForCancelBtn;
    protected final List<CompleteProcTaskAction> completeProcTaskActions = new ArrayList<>();
    protected ProcAction.AfterActionListener afterClaimTaskListener;
    protected ProcAction.BeforeActionPredicate beforeActionPredicateApprove;
    protected ProcAction.BeforeActionPredicate beforeActionPredicateOther;
    protected ProcAction.BeforeActionPredicate beforeCancelProcessPredicate;
    protected ProcAction.AfterActionListener afterCancelProcessListener;
    protected Consumer<AfterCloseEvent> afterCloseMessagesListener;
    private String SHARE_SUBJECT_KEY = "share.subject";
    private String SHARE_TEXT_KEY = "share.text";
    private String SHARE_NEW_MESSAGE_SCREEN_ID = "ddcui$send-message";
    @Inject
    private BpmEntitiesService bpmEntitiesService;
    @Inject
    private ProcessFormService processFormService;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private Messages messages;
    @Inject
    private ProcessMessagesService processMessagesService;
    @Inject
    private UserSession userSession;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private MetadataTools metadataTools;

    protected void initEntityByProcess(ProcInstance procInstance, HBoxLayout actionBox, List<User> userListCancelProc) {
        isProcess = true;
        procInstanceEntity = procInstance;
        actionsBoxForProcessBtn = actionBox;
        userListForCancelBtn = userListCancelProc;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        updateFormCaption();
    }

    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        updateFormCaption();
    }

    private void updateFormCaption() {
        this
                .getWindow()
                .setCaption(
                        getEditFormCaption(
                                MessageHelperITK.getCaptionByClassName(
                                        this.getClass().getSimpleName()
                                )
                        )
                );
    }

    private String getEditFormCaption(String captionKey) {
        Messages messages = AppBeans.get(Messages.class);
        EntityStates entityStates = AppBeans.get(EntityStates.class);
        if (entityStates.isNew(this.getEditedEntity())) {
            return messages.getMessage(this.getClass(), captionKey)
                    + ": "
                    + messages.getMainMessage("form.msg.caption.create");
        } else {
            return messages.getMessage(this.getClass(), captionKey)
                    + ": "
                    + ((StandardEntityITK) this.getEditedEntity()).getCaption();
        }
    }

    protected void initFormByProcess() {
        if (isProcess) {
            List<ProcTask> procTasks = bpmEntitiesService.findActiveProcTasksForCurrentUser(
                    procInstanceEntity,
                    BpmConstants.Views.PROC_TASK_COMPLETE
            );
            procTask = procTasks.isEmpty() ? null : procTasks.get(0);
            if (procTask != null && procTask.getProcActor() != null) {
                initCompleteTaskUI();
            } else if (procTask != null && procTask.getProcActor() == null) {
                initClaimTaskUI();
            }
            if (procInstanceEntity.getActive() && userListForCancelBtn.contains(userSession.getUser())) {
                initCancelAction();
            }
        }
    }

    private void initCancelAction() {
        Button cancelProcessBtn = uiComponents.create(Button.class);
        cancelProcessBtn.setWidth("100%");
        cancelProcessBtn.setCaption(messages.getMainMessage("form.cancelProcTaskBtn"));
        CancelProcessAction cancelProcessAction = new CancelProcessAction(procInstanceEntity);
        cancelProcessAction.addBeforeActionPredicate(beforeCancelProcessPredicate);
        cancelProcessAction.addAfterActionListener(afterCancelProcessListener);
        cancelProcessBtn.setAction(cancelProcessAction);
        actionsBoxForProcessBtn.add(cancelProcessBtn);
    }

    private void initCompleteTaskUI() {
        Map<String, ProcFormDefinition> outcomesWithForms = processFormService.getOutcomesWithForms(procTask);
        if (!outcomesWithForms.isEmpty()) {
            for (Map.Entry<String, ProcFormDefinition> entry : outcomesWithForms.entrySet()) {
                CompleteProcTaskAction action = new CompleteProcTaskAction(
                        procTask,
                        processMessagesService.getMessage(
                                procInstanceEntity.getProcDefinition().getActId(), entry.getKey()
                        ),
                        entry.getValue()
                );
                action.setCaption(entry.getKey());
                if (entry.getKey().equals("Погоджено")) {
                    action.addBeforeActionPredicate(beforeActionPredicateApprove);
                } else {
                    action.addBeforeActionPredicate(beforeActionPredicateOther);
                }
                completeProcTaskActions.add(action);
            }
        } else {
            ProcFormDefinition form = processFormService.getDefaultCompleteTaskForm(
                    procInstanceEntity.getProcDefinition()
            );
            CompleteProcTaskAction action = new CompleteProcTaskAction(procTask, BpmConstants.DEFAULT_TASK_OUTCOME, form);
            action.setCaption(messageBundle.getMessage("completeTask"));
            completeProcTaskActions.add(action);
        }

        for (CompleteProcTaskAction completeProcTaskAction : completeProcTaskActions) {
            completeProcTaskAction.addAfterActionListener(this::closeWithCommit);
            Button actionBtn = uiComponents.create(Button.class);
            actionBtn.setWidth("100%");
            actionBtn.setAction(completeProcTaskAction);
            actionsBoxForProcessBtn.add(actionBtn);
        }
    }

    private void initClaimTaskUI() {
        Button claimTaskBtn = uiComponents.create(Button.class);
        claimTaskBtn.setWidth("100%");
        claimTaskBtn.setCaption(messages.getMainMessage("form.claimTaskBtn"));

        ClaimProcTaskAction claimProcTaskAction = new ClaimProcTaskAction(procTask);
        claimTaskBtn.setAction(claimProcTaskAction);
        claimProcTaskAction.addAfterActionListener(afterClaimTaskListener);
        actionsBoxForProcessBtn.add(claimTaskBtn);
    }

    protected void createMessagesForEntity() {
        screenBuilders.editor(SendMessageEntity.class, this)
                .withScreenId(SHARE_NEW_MESSAGE_SCREEN_ID)
                .withInitializer(message -> {
                    StandardEntity shareable = getEditedEntity();
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
                .show().addAfterCloseListener(afterCloseMessagesListener);
    }
}
