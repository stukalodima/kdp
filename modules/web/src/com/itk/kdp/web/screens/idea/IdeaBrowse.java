package com.itk.kdp.web.screens.idea;

import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.Idea;

@UiController("kdp_Idea.browse")
@UiDescriptor("idea-browse.xml")
@LookupComponent("ideasTable")
@LoadDataBeforeShow
public class IdeaBrowse extends StandardLookup<Idea> {
}