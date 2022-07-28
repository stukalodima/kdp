package com.itk.kdp.web.screens.form;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Dialogs;
import com.itk.kdp.entity.helper.MessageHelperITK;

import java.io.IOException;

public abstract class DialogsITK {

    private static final Messages messages = AppBeans.get(Messages.class);

    @SuppressWarnings("all")
    public static Dialogs.MessageDialogBuilder getDialogForImportSuccess(Dialogs dialogs, Class form) {
        return dialogs.createMessageDialog()
                .withCaption(messages.getMainMessage("form.msg.import.caption"))
                .withMessage(messages.getMessage(
                        form, MessageHelperITK.getCaptionByClassName(form.getSimpleName())
                )
                        + " " + messages.getMainMessage("form.msg.import.text"));
    }

    @SuppressWarnings("all")
    public static Dialogs.MessageDialogBuilder getDialogForImportError(Dialogs dialogs, IOException e, Class form) {
        return dialogs.createMessageDialog()
                .withCaption(messages.getMainMessage("form.msg.importError.caption"))
                .withMessage(messages.getMainMessage("form.msg.importError.text") + " " +
                        messages.getMessage(
                                form, MessageHelperITK.getCaptionByClassName(form.getSimpleName()) + "!"
                        )
                        + "\n" + e.getMessage());
    }
}
