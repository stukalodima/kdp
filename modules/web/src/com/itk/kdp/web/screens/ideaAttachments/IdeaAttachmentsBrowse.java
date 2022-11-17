package com.itk.kdp.web.screens.ideaAttachments;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.IdeaAttachments;

@UiController("kdp_IdeaAttachments.browse")
@UiDescriptor("idea-attachments-browse.xml")
@LookupComponent("ideaAttachmentsTable")
@LoadDataBeforeShow
public class IdeaAttachmentsBrowse extends StandardLookup<IdeaAttachments> {
}