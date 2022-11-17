package com.itk.kdp.web.screens.ideaAttachments;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.IdeaAttachments;

@UiController("kdp_IdeaAttachments.edit")
@UiDescriptor("idea-attachments-edit.xml")
@EditedEntityContainer("ideaAttachmentsDc")
@LoadDataBeforeShow
public class IdeaAttachmentsEdit extends StandardEditor<IdeaAttachments> {
}