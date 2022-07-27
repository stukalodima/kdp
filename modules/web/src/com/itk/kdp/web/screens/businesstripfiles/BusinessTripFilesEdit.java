package com.itk.kdp.web.screens.businesstripfiles;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.bpm.service.ProcessFormService;
import com.haulmont.bpm.service.ProcessRuntimeService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.entity.BusinessTripFiles;
import com.itk.kdp.service.EmployeeService;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@UiController("kdp_BusinessTripFiles.edit")
@UiDescriptor("business-trip-files-edit.xml")
@EditedEntityContainer("businessTripFilesDc")
@LoadDataBeforeShow
public class BusinessTripFilesEdit extends StandardEditor<BusinessTripFiles> {
    @Inject
    private EmployeeService employeeService;
    @Inject
    private UserSession userSession;
    @Inject
    private DataManager dataManager;

    @Subscribe
    public void onInitEntity(InitEntityEvent<BusinessTripFiles> event) {
        event.getEntity().setAuthor(employeeService.getEmployeeByUser(userSession.getUser()));
        ProcInstance procInstance = event.getEntity().getBusinessTrip().getProcInstance();
        if (!Objects.isNull(procInstance)) {
            List<ProcTask> procTaskList = dataManager.load(ProcTask.class)
                    .query("select e from bpm$ProcTask e\n" +
                            "where e.procInstance.id = :procInstance\n" +
                            "order by e.createTs desc")
                    .parameter("procInstance", procInstance.getId())
                    .view("procTask-frame")
                    .list();
            if (procTaskList.isEmpty()) {
                event.getEntity().setTask("Проект заявки");
            } else {
                event.getEntity().setTask(procTaskList.get(0).getLocName());
            }
        } else {
            event.getEntity().setTask("Проект заявки");
        }
    }

}