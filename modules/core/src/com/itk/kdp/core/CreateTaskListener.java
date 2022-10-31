package com.itk.kdp.core;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.app.FileStorageAPI;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.entity.BusinessTrip;
import com.itk.kdp.entity.BusinessTripFiles;
import com.itk.kdp.entity.VacationBalance;
import com.itk.kdp.entity.VacationRequest;
import com.itk.kdp.service.EmailNotificationsServiceBean;
import com.itk.kdp.service.EmailService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Component(CreateTaskListener.NAME)
public class CreateTaskListener implements TaskListener {
    public static final String NAME = "kdp_CreateTaskListener";

    private Expression procRole;
    private Expression sendFile;
    private EmailAttachment[] emailAttachments;
    @Override
    public void notify(DelegateTask delegateTask) {

        String procRoleCode = procRole != null ? (String) procRole.getValue(delegateTask) : "";
        boolean sendFileValue = sendFile != null ? "true".equals(sendFile.getValue(delegateTask)) : false;

        EmailService emailService = AppBeans.get(EmailService.class);
        Messages messages = AppBeans.get(Messages.class);
        DataManager dataManager = AppBeans.get(DataManager.class);
        Persistence persistence = AppBeans.get(Persistence.class);
        Metadata metadata = AppBeans.get(Metadata.class);
        FileStorageAPI fileStorageAPI = AppBeans.get(FileStorageAPI.class);

        RuntimeService runtimeService = delegateTask.getExecution().getEngineServices().getRuntimeService();

        Set<IdentityLink> users = delegateTask.getCandidates();
        BusinessTrip businessTrip = null;
        VacationRequest vacationRequest = null;

        Object object = EmailNotificationsServiceBean.getObjectFromDelegateTask(delegateTask);
        if (object.getClass().equals(BusinessTrip.class)) {
            businessTrip = (BusinessTrip) object;
        } else if (object.getClass().equals(VacationRequest.class)) {
            vacationRequest = (VacationRequest) object;
        }

        if (sendFileValue) {

            View viewProcInstance = ViewBuilder.of(ProcInstance.class)
                    .addAll("procActors.user", "procActors.procRole")
                    .build();

            ProcInstance procInstance = entityManager
                    .createQuery("select e from bpm$ProcInstance e where e.actProcessInstanceId = :executionId", ProcInstance.class)
                    .setParameter("executionId", executionId)
                    .setView(viewProcInstance)
                    .getFirstResult();

            List<User> userList = new ArrayList<>();
            Set<ProcActor> procActorSet = Objects.requireNonNull(procInstance).getProcActors();
            for (ProcActor procActor : procActorSet) {
                if (procActor.getProcRole().getCode().equals(procRoleCode)) {
                    userList.add(procActor.getUser());
                }
            }

            List<EmailAttachment> emailAttachmentList = new ArrayList<>();
            for (BusinessTripFiles businessTripFiles : businessTrip.getDocuments()
            ) {
                if (!Objects.isNull(businessTripFiles.getAuthor()) && userList.contains(businessTripFiles.getAuthor().getUser())) {
                    try {
                        emailAttachmentList.add(new EmailAttachment(fileStorageAPI.loadFile(businessTripFiles.getDocument()), businessTripFiles.getDocument().getName()));
                    } catch (FileStorageException e) {
                        continue;
                    }
                }
            }
            if (!emailAttachmentList.isEmpty())
                emailAttachments = emailAttachmentList.toArray(new EmailAttachment[emailAttachmentList.size()]);

        }
        if (users.isEmpty()) {
            String userStr = delegateTask.getAssignee();

            UUID userId = UUID.fromString(userStr);
            User user = getUser(dataManager, userId);
            if (user == null || StringUtil.isBlank(user.getEmail())) {
                return;
            }
            if (!Objects.isNull(businessTrip)) {
                sendMail(delegateTask, emailService, messages, businessTrip, user, emailAttachments);
            }
            if (!Objects.isNull(vacationRequest)) {
                sendMailVacation(delegateTask, emailService, messages, vacationRequest, user);
            }
        } else {
            for (IdentityLink userIdent : users) {
                UUID userId = UUID.fromString(userIdent.getUserId());
                User user = getUser(dataManager, userId);
                if (user == null || StringUtil.isBlank(user.getEmail())) {
                    continue;
                }
                if (!Objects.isNull(businessTrip)) {
                    sendMail(delegateTask, emailService, messages, businessTrip, user, emailAttachments);
                }
                if (!Objects.isNull(vacationRequest)) {
                    sendMailVacation(delegateTask, emailService, messages, vacationRequest, user);
                }
            }
        }
    }

    private void sendMailVacation(DelegateTask delegateTask, EmailService emailService, Messages messages, VacationRequest vacationRequest, User user) {
        Map<String, Serializable> mapParam = new HashMap<>();

        String emptyString = messages.getMessage(BusinessTrip.class, "BusinessTrip.emptyString");

        mapParam.put("systemName", messages.getMessage(CreateTaskListener.class, "mail.systemName"));
        mapParam.put("welcome", messages.getMessage(CreateTaskListener.class, "mail.welcome"));
        mapParam.put("userName", user.getName());
        mapParam.put("YouHaveNewTask", messages.getMessage(CreateTaskListener.class, "mail.newTask"));
        mapParam.put("taskName", delegateTask.getName());
        mapParam.put("BaseData", messages.getMessage(CreateTaskListener.class, "mail.BaseData"));
        mapParam.put("titleFIO", messages.getMessage(VacationRequest.class, "VacationBalance.employee"));
        mapParam.put("FIO", checkStringFieldIsEmpty(vacationRequest.getEmployee().getCaption(), emptyString));
        mapParam.put("titleCompany", messages.getMessage(VacationRequest.class, "VacationRequest.company"));
        mapParam.put("company", checkStringFieldIsEmpty(vacationRequest.getCompany().getCaption(), emptyString));
        mapParam.put("titleDays", messages.getMessage(VacationRequest.class, "VacationRequest.vacationDays"));
        mapParam.put("Days", checkStringFieldIsEmpty(vacationRequest.getVacationDays().toString(), emptyString));
        mapParam.put("titleStartDate", messages.getMessage(VacationRequest.class, "VacationRequest.dateFrom"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        mapParam.put("startDate", checkStringFieldIsEmpty(simpleDateFormat.format(vacationRequest.getDateFrom()), emptyString));
        mapParam.put("titleEndDate", messages.getMessage(VacationRequest.class, "VacationRequest.dateBy"));
        mapParam.put("endDate", checkStringFieldIsEmpty(simpleDateFormat.format(vacationRequest.getDateBy()), emptyString));
        mapParam.put("titleNumber", messages.getMessage(VacationRequest.class, "VacationRequest.applicationNumber"));
        mapParam.put("number", checkStringFieldIsEmpty(vacationRequest.getApplicationNumber().toString(), emptyString));

        emailService.sendEmail(user.getEmail(),
                messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerEmailCaptionVacation"),
                messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerEmailTemplateVacation"),
                mapParam);
    }

    private User getUser(DataManager dataManager, UUID userId) {
        return dataManager.load(LoadContext.create(User.class)
                .setId(userId)
                .setView(View.LOCAL));
    }

    private void sendMail(DelegateTask delegateTask, EmailService emailService, Messages messages, BusinessTrip businessTrip, User user, EmailAttachment[] emailAttachments) {
        Map<String, Serializable> mapParam = new HashMap<>();

        String emptyString = messages.getMessage(BusinessTrip.class, "BusinessTrip.emptyString");

        mapParam.put("systemName", messages.getMessage(CreateTaskListener.class, "mail.systemName"));
        mapParam.put("welcome", messages.getMessage(CreateTaskListener.class, "mail.welcome"));
        mapParam.put("userName", user.getName());
        mapParam.put("YouHaveNewTask", messages.getMessage(CreateTaskListener.class, "mail.newTask"));
        mapParam.put("taskName", delegateTask.getName());
        mapParam.put("BaseData", messages.getMessage(CreateTaskListener.class, "mail.BaseData"));
        mapParam.put("titleFIO", messages.getMessage(BusinessTrip.class, "BusinessTrip.employee.fio"));
        mapParam.put("FIO", checkStringFieldIsEmpty(businessTrip.getEmployee().getFio(), emptyString));
        mapParam.put("titleCompany", messages.getMessage(BusinessTrip.class, "BusinessTrip.organization"));
        mapParam.put("company", checkStringFieldIsEmpty(businessTrip.getOrganization().getOrganizationsUa(), emptyString));
        mapParam.put("titleDestination", messages.getMessage(BusinessTrip.class, "BusinessTrip.destination"));
        mapParam.put("destination", checkStringFieldIsEmpty(businessTrip.getDestination(), emptyString));
        mapParam.put("titleCompanyName", messages.getMessage(BusinessTrip.class, "BusinessTrip.companyName"));
        mapParam.put("companyName", checkStringFieldIsEmpty(businessTrip.getCompanyName(), emptyString));
        mapParam.put("titlePurpose", messages.getMessage(BusinessTrip.class, "BusinessTrip.purpose"));
        mapParam.put("purpose", checkStringFieldIsEmpty(businessTrip.getPurpose().getNameUa(), emptyString));
        mapParam.put("titleTransport", messages.getMessage(BusinessTrip.class, "BusinessTrip.transport"));
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        if (Boolean.TRUE.equals(businessTrip.getPlainTransport())) {
            stringBuilder.append(messages.getMessage(BusinessTrip.class, "BusinessTrip.plainTransport"));
        }
        if (Boolean.TRUE.equals(businessTrip.getTrainTransport())) {
            stringBuilder
                    .append(checkStringIsEmpty(stringBuilder))
                    .append(messages.getMessage(BusinessTrip.class, "BusinessTrip.trainTransport"));
        }
        if (Boolean.TRUE.equals(businessTrip.getBusTransport())) {
            stringBuilder
                    .append(checkStringIsEmpty(stringBuilder))
                    .append(messages.getMessage(BusinessTrip.class, "BusinessTrip.busTransport"));
        }
        if (Boolean.TRUE.equals(businessTrip.getAutoCompanyTransport())) {
            stringBuilder
                    .append(checkStringIsEmpty(stringBuilder))
                    .append(messages.getMessage(BusinessTrip.class, "BusinessTrip.autoCompanyTransport"));
        }
        if (Boolean.TRUE.equals(businessTrip.getAutoSelfTransport())) {
            stringBuilder
                    .append(checkStringIsEmpty(stringBuilder))
                    .append(messages.getMessage(BusinessTrip.class, "BusinessTrip.autoSelfTransport"));
        }
        mapParam.put("transport", stringBuilder.toString());
        mapParam.put("titleStartDate", messages.getMessage(BusinessTrip.class, "BusinessTrip.startDate"));
        mapParam.put("startDate", checkStringFieldIsEmpty(simpleDateFormat.format(businessTrip.getStartDate()), emptyString));
        mapParam.put("titleEndDate", messages.getMessage(BusinessTrip.class, "BusinessTrip.endDate"));
        mapParam.put("endDate", checkStringFieldIsEmpty(simpleDateFormat.format(businessTrip.getEndDate()), emptyString));
        mapParam.put("titleBudget", messages.getMessage(BusinessTrip.class, "BusinessTrip.budgetTrip"));
        mapParam.put("budget", checkStringFieldIsEmpty(businessTrip.getBudgetTrip(), emptyString));
        mapParam.put("titleIsBudget", messages.getMessage(BusinessTrip.class, "BusinessTrip.isBudget"));
        mapParam.put("isBudget", checkStringFieldIsEmpty(businessTrip.getIsBudget(), emptyString));
        mapParam.put("titleNumber", messages.getMessage(BusinessTrip.class, "BusinessTrip.number"));
        mapParam.put("number", checkStringFieldIsEmpty(businessTrip.getNumber().toString(), emptyString));
        mapParam.put("approve", messages.getMessage(CreateTaskListener.class, "mail.approve"));
        mapParam.put("terminate", messages.getMessage(CreateTaskListener.class, "mail.terminate"));

        if(Objects.isNull(emailAttachments)) {
            emailService.sendEmail(user.getEmail(),
                    messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerEmailCaption"),
                    messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerEmailTemplate"),
                    mapParam);
        } else {
            emailService.sendEmail(user.getEmail(),
                    messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerEmailCaption"),
                    messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerEmailTemplate"),
                    mapParam, emailAttachments);
        }
    }

    String checkStringIsEmpty(StringBuilder stringBuilder) {
        return stringBuilder.toString().isEmpty() ? "" : "/";
    }

    String checkStringFieldIsEmpty(String field, String emptyString) {
        return Objects.isNull(field) ? emptyString : field;
    }
}