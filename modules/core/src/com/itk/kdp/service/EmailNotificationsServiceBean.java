package com.itk.kdp.service;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.itk.kdp.entity.VacationRequest;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service(EmailNotificationsService.NAME)
public class EmailNotificationsServiceBean implements EmailNotificationsService {

    public static Object getObjectFromDelegateTask (DelegateTask delegateTask){

        Persistence persistence = AppBeans.get(Persistence.class);
        Metadata metadata = AppBeans.get(Metadata.class);

        RuntimeService runtimeService = delegateTask.getExecution().getEngineServices().getRuntimeService();

        String executionId = delegateTask.getExecutionId();
        UUID entityId = (UUID) runtimeService.getVariable(executionId, "entityId");
        String entityName = (String) runtimeService.getVariable(executionId, "entityName");

        MetaClass metaClass = metadata.getClass(entityName);

        EntityManager entityManager = persistence.getEntityManager();

        if (Objects.requireNonNull(metaClass).getJavaClass().equals(VacationRequest.class)) {
            return entityManager.find(Objects.requireNonNull(metaClass).getJavaClass(), entityId);
        } else if (Objects.requireNonNull(metaClass).getJavaClass().equals(VacationRequest.class)) {
            return entityManager.find(Objects.requireNonNull(metaClass).getJavaClass(), entityId);
        } else {
            return null;
        }
    }

}