package com.itk.kdp.web;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;
import javax.annotation.Nullable;
import javax.inject.Inject;

@Component(ActivitiTaskAlreadyClaimedExceptionHandler.NAME)
public class ActivitiTaskAlreadyClaimedExceptionHandler extends AbstractUiExceptionHandler {
    public static final String NAME = "kdp_ActivitiTaskAlreadyClaimedExceptionHandler";
    @Inject
    private Messages messages;

    public ActivitiTaskAlreadyClaimedExceptionHandler() {
        super("org.activiti.engine.ActivitiTaskAlreadyClaimedException");
    }
    @Override
    protected void doHandle(String className, String message, @Nullable Throwable throwable, UiContext context) {

        if (throwable != null) {
        context.getDialogs().createExceptionDialog()
                .withThrowable(throwable)
                .withCaption(messages.getMainMessage("ActivitiTaskAlreadyClaimedException.ErrorCaption"))
                .withMessage(messages.getMainMessage("ActivitiTaskAlreadyClaimedException.ErrorMessage"))
                .show();
        } else {
            context.getNotifications().create(Notifications.NotificationType.ERROR)
                    .withCaption(messages.getMainMessage("ActivitiTaskAlreadyClaimedException.ErrorCaption"))
                    .withDescription(messages.getMainMessage("ActivitiTaskAlreadyClaimedException.ErrorMessage"))
                    .show();
        }
    }
}