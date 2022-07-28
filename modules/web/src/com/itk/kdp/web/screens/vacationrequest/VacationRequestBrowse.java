package com.itk.kdp.web.screens.vacationrequest;

import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.screen.*;
import com.itk.kdp.entity.BusinessTrip;
import com.itk.kdp.entity.VacationRequest;

import javax.annotation.Nullable;
import javax.inject.Inject;

@UiController("kdp_VacationRequest.browse")
@UiDescriptor("vacation-request-browse.xml")
@LookupComponent("vacationRequestsTable")
@LoadDataBeforeShow
public class VacationRequestBrowse extends StandardLookup<VacationRequest> {
    @Inject
    private GroupTable<VacationRequest> vacationRequestsTable;

    @Subscribe
    public void onInit(InitEvent event) {
        vacationRequestsTable.setStyleProvider(new GroupTable.GroupStyleProvider<VacationRequest>() {

            @Nullable
            @Override
            public String getStyleName(VacationRequest entity, @Nullable String property) {
                if (property == null) {
                    switch (entity.getStatus()){
                        case "На согласовании": return "startProc1";
                        case "Согласована": return "approved1";
                        case "Не согласована": return "terminated1";
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
    }
}