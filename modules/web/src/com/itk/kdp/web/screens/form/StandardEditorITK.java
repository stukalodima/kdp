package com.itk.kdp.web.screens.form;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.itk.kdp.base.itk.StandardEntityITK;
import com.itk.kdp.entity.helper.MessageHelperITK;

public class StandardEditorITK<O extends StandardEntity> extends StandardEditor<O> {
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
}
