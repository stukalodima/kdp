package com.itk.kdp.web.screens.businesstrip;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.itk.kdp.entity.BusinessTrip;
import de.diedavids.cuba.userinbox.web.WithEntitySharingSupport;

import com.haulmont.reports.gui.actions.list.ListPrintFormAction;

import javax.annotation.Nullable;
import javax.inject.Inject;

@UiController("kdp_BusinessTrip.browse")
@UiDescriptor("business-trip-browse.xml")
@LookupComponent("businessTripsTable")
@LoadDataBeforeShow
@Route("business-trip")
public class BusinessTripBrowse extends StandardLookup<BusinessTrip> implements WithEntitySharingSupport {
    @Inject
    private GroupTable<BusinessTrip> businessTripsTable;
    @Inject
    private ButtonsPanel buttonsPanel;
    @Inject
    private Button listPrintBtn;
    @Inject
    private Actions actions;

    @Subscribe
    public void onInit(InitEvent event) {
        businessTripsTable.setStyleProvider(new GroupTable.GroupStyleProvider<BusinessTrip>() {
            @Nullable
            @Override
            public String getStyleName(BusinessTrip entity, @Nullable String property) {
                if (property == null) {
                    switch (entity.getStatus()){
                        case "На погодженні": return "startProc1";
                        case "Погоджена": return "approved1";
                        case "Не погоджена": return "terminated1";
                    }
                }
                return null;
            }

            @Nullable
            @Override
            public String getStyleName(GroupInfo info) {
                return null;
            }
        });

        ListPrintFormAction listPrintFormAction = actions.create(ListPrintFormAction.class, "listPrint");
        businessTripsTable.addAction(listPrintFormAction);
        listPrintBtn.setAction(listPrintFormAction);
    }

    @Override
    public Table getListComponent() {
        return businessTripsTable;
    }

    @Override
    public ButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }


}