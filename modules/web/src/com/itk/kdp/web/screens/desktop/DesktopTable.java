package com.itk.kdp.web.screens.desktop;

import com.google.common.base.Strings;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.bpm.gui.proctask.ProcTaskBrowse;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.config.WindowConfig;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.global.UserSession;
import com.itk.kdp.entity.BusinessTrip;
import com.itk.kdp.entity.VacationRequest;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;

import static com.haulmont.cuba.gui.components.Window.COMMIT_ACTION_ID;

@UiController("kdp_DesktopTable")
@UiDescriptor("desktop-table.xml")
@Route("desktop")
public class DesktopTable extends Screen {
    @Inject
    private GroupTable<ProcTask> myTasksTable;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private ReferenceToEntitySupport referenceToEntitySupport;
    @Inject
    private CollectionLoader<ProcTask> myTasksDl;
    @Inject
    private UserSession userSession;

    @Subscribe
    public void onInit(InitEvent event) {
        myTasksTable.addGeneratedColumn("assigned", entity -> {
            CheckBox checkBox = uiComponents.create(CheckBox.class);
            checkBox.setValue(entity.getProcActor() != null);
            return checkBox;
        });
        myTasksTable.addGeneratedColumn("company", this::getCompanyFromTask);
        myTasksTable.addGeneratedColumn("document" , this::getDocumentFromTask);
        myTasksTable.addGeneratedColumn("employee" , this::getEmployeeFromTask);
        myTasksDl.setParameter("userId", userSession.getUser().getId());
        myTasksDl.load();
    }

    private Component getCompanyFromTask(ProcTask procTask) {
        Entity entity = getEntityByTask(procTask);
        if (!Objects.isNull(entity) && entity.getClass().equals(BusinessTrip.class)) {
            BusinessTrip businessTrip = dataManager.reload((BusinessTrip) entity, "businessTrip-view");
            Table.PlainTextCell textCell = new Table.PlainTextCell(businessTrip.getOrganization().getCaption());
            return textCell;
        } else if (!Objects.isNull(entity) && entity.getClass().equals(VacationRequest.class)) {
            VacationRequest vacationRequest = dataManager.reload((VacationRequest) entity, "vacationRequest-view");
            Table.PlainTextCell textCell = new Table.PlainTextCell(vacationRequest.getCompany().getCaption());
            return textCell;
        }
        return null;
    }
    private Component getDocumentFromTask(ProcTask procTask) {
        Entity entity = getEntityByTask(procTask);
        if (!Objects.isNull(entity) && entity.getClass().equals(BusinessTrip.class)) {
            BusinessTrip businessTrip = dataManager.reload((BusinessTrip) entity, "businessTrip-view");
            Table.PlainTextCell textCell = new Table.PlainTextCell(businessTrip.getCaption());
            return textCell;
        } else if (!Objects.isNull(entity) && entity.getClass().equals(VacationRequest.class)) {
            VacationRequest vacationRequest = dataManager.reload((VacationRequest) entity, "vacationRequest-view");
            Table.PlainTextCell textCell = new Table.PlainTextCell(vacationRequest.getCaption());
            return textCell;
        }
        return null;
    }
    private Component getEmployeeFromTask(ProcTask procTask) {
        Entity entity = getEntityByTask(procTask);
        if (!Objects.isNull(entity) && entity.getClass().equals(BusinessTrip.class)) {
            BusinessTrip businessTrip = dataManager.reload((BusinessTrip) entity, "businessTrip-view");
            Table.PlainTextCell textCell = new Table.PlainTextCell(businessTrip.getEmployee().getCaption());
            return textCell;
        } else if (!Objects.isNull(entity) && entity.getClass().equals(VacationRequest.class)) {
            VacationRequest vacationRequest = dataManager.reload((VacationRequest) entity, "vacationRequest-view");
            Table.PlainTextCell textCell = new Table.PlainTextCell(vacationRequest.getEmployee().getCaption());
            return textCell;
        }
        return null;
    }

    @Subscribe("myTasksTable.edit")
    public void onMyTasksTableEdit(Action.ActionPerformedEvent event) {
        ProcTask selectedTask = myTasksTable.getSingleSelected();
        ProcInstance procInstance;
        if (selectedTask != null) {
            procInstance = selectedTask.getProcInstance();
        } else {
            return;
        }
        Object entityId = procInstance.getObjectEntityId();

        MetaClass metaClass = metadata.getClass(procInstance.getEntityName());
        if (Objects.isNull(metaClass)) {
            return;
        }

        LoadContext<Entity> ctx = new LoadContext<>(metaClass).setQuery(
                LoadContext.createQuery(String.format("select e from %s e where e.%s = :entityId",
                                metaClass.getName(),
                                referenceToEntitySupport.getPrimaryKeyForLoadingEntity(metaClass)))
                        .setParameter("entityId", entityId));
        Entity entity = dataManager.load(ctx);

        Screen screen = screenBuilders.editor(metaClass.getJavaClass(), this).
                editEntity(entity)
                .build();
        screen.addAfterCloseListener(e -> myTasksDl.load());
        screen.show();
    }

    private Entity getEntityByTask(ProcTask procTask) {
        ProcInstance procInstance = procTask.getProcInstance();
        Object entityId = procInstance.getObjectEntityId();

        MetaClass metaClass = metadata.getClass(procInstance.getEntityName());
        if (!Objects.isNull(metaClass)) {
            LoadContext<Entity> ctx = new LoadContext<>(metaClass).setQuery(
                    LoadContext.createQuery(String.format("select e from %s e where e.%s = :entityId",
                                    metaClass.getName(),
                                    referenceToEntitySupport.getPrimaryKeyForLoadingEntity(metaClass)))
                            .setParameter("entityId", entityId));
            Entity entity = dataManager.load(ctx);
            return entity;
        }
        return null;
    }

}