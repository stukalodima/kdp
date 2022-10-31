package com.itk.kdp.core;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Configuration;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.config.MailNotificationsConfig;
import com.itk.kdp.entity.BusinessTrip;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.VacationRequest;
import com.itk.kdp.service.EmailNotificationsServiceBean;
import com.itk.kdp.service.EmailService;
import com.itk.kdp.service.GetDepartmensService;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component(CompleteTaskListener.NAME)
public class CompleteTaskListener extends CreateTaskListener {
    public static final String NAME = "kdp_CompleteTaskListener";

    private static Logger log = LoggerFactory.getLogger(CompleteTaskListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {


        Object object = EmailNotificationsServiceBean.getObjectFromDelegateTask(delegateTask);

        Organizations notificationCompany = AppBeans.get(Configuration.class)
                .getConfig(MailNotificationsConfig.class)
                .getOrganizationForMailNotifications();

        Departments notificationDepartment = AppBeans.get(Configuration.class)
                .getConfig(MailNotificationsConfig.class)
                .getDepartmentForMailNotifications();

        if (object == null || notificationCompany == null) {
            log.warn("Configurations; getOrganizationForMailNotifications == null \n or getOrganizationForMailNotifications == notificationCompany");
            return;
        }

        List<Departments> departments = AppBeans.get(GetDepartmensService.class).getDepartmentsFilter(notificationDepartment);

        if (departments.isEmpty()) {
            log.warn("List departments to send messages is empty");
            return;
        }

        EmailService emailService = AppBeans.get(EmailService.class);
        Messages messages = AppBeans.get(Messages.class);
        DataManager dataManager = AppBeans.get(DataManager.class);

        BusinessTrip businessTrip = null;
        VacationRequest vacationRequest = null;

        if (object.getClass().equals(BusinessTrip.class)) {
            businessTrip = (BusinessTrip) object;
        } else if (object.getClass().equals(VacationRequest.class)) {
            vacationRequest = (VacationRequest) object;
        }

        if ((businessTrip != null && notificationCompany.equals(businessTrip.getOrganization())
                || (vacationRequest != null && notificationCompany.equals(vacationRequest.getCompany())))) {
            List<User> users = getDepartmentUsers(departments, dataManager);
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
                    if (vacationRequest != null) {
                        sendMailVacationNotificationsRMO(emailService, messages, vacationRequest, user);
                    } else {
                        sendMailBusinessTripNotificationsRMO(emailService, messages, businessTrip, user);
                    }

                }
            }
        }
    }

    private List<User> getDepartmentUsers(List<Departments> departmentsList, DataManager dataManager) {
        List<User> userList = dataManager.load(User.class)
                .query("select e.user from kdp_Employees e where e.department in :departmendList")
                .parameter("departmendList", departmentsList)
                .view("_base")
                .list();

        if (userList.isEmpty()) {
            return null;
        } else {
            return userList;
        }
    }

    private void sendMailVacationNotificationsRMO(EmailService emailService, Messages messages, VacationRequest vacationRequest, User user) {

        Map<String, Serializable> mapParam = new HashMap<>();

        String emptyString = messages.getMessage(BusinessTrip.class, "BusinessTrip.emptyString");

        mapParam.put("systemName", messages.getMessage(CreateTaskListener.class, "mail.systemName"));
        mapParam.put("welcome", messages.getMessage(CreateTaskListener.class, "mail.welcome"));
        mapParam.put("userName", user.getName());
        mapParam.put("NotificationFromVacation", messages.getMessage(CreateTaskListener.class, "mail.completeTaskVacationNotification"));
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

        emailService.sendEmail(user.getEmail(),
                messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerNotificationEmailCaptionVacation"),
                messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerEmailTemplateNotificationsVacation"),
                mapParam);
    }

    private void sendMailBusinessTripNotificationsRMO(EmailService emailService, Messages messages, BusinessTrip businessTrip, User user) {
        int businessTripDays = 0;

        Map<String, Serializable> mapParam = new HashMap<>();

        String emptyString = messages.getMessage(BusinessTrip.class, "BusinessTrip.emptyString");

        if (!Objects.isNull(businessTrip.getStartDate()) && !(Objects.isNull(businessTrip.getEndDate()))) {
            long milliseconds = businessTrip.getEndDate().getTime() - businessTrip.getStartDate().getTime();
            businessTripDays = ((int) (milliseconds / (24 * 60 * 60 * 1000)) + 1);
        }

        mapParam.put("systemName", messages.getMessage(CreateTaskListener.class, "mail.systemName"));
        mapParam.put("welcome", messages.getMessage(CreateTaskListener.class, "mail.welcome"));
        mapParam.put("userName", user.getName());
        mapParam.put("NotificationFromVacation", messages.getMessage(CreateTaskListener.class, "mail.completeTaskBusinessTripNotification"));
        mapParam.put("BaseData", messages.getMessage(CreateTaskListener.class, "mail.BaseData"));
        mapParam.put("titleFIO", messages.getMessage(VacationRequest.class, "VacationBalance.employee"));
        mapParam.put("FIO", checkStringFieldIsEmpty(businessTrip.getEmployee().getFio(), emptyString));
        mapParam.put("titleCompany", messages.getMessage(VacationRequest.class, "VacationRequest.company"));
        mapParam.put("company", checkStringFieldIsEmpty(businessTrip.getOrganization().getOrganizationsUa(), emptyString));
        mapParam.put("titleDays", messages.getMessage(VacationRequest.class, "VacationRequest.vacationDays"));
        mapParam.put("Days", checkStringFieldIsEmpty(Integer.toString(businessTripDays), emptyString));
        mapParam.put("titleStartDate", messages.getMessage(VacationRequest.class, "VacationRequest.dateFrom"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        mapParam.put("startDate", checkStringFieldIsEmpty(simpleDateFormat.format(businessTrip.getStartDate()), emptyString));
        mapParam.put("titleEndDate", messages.getMessage(VacationRequest.class, "VacationRequest.dateBy"));
        mapParam.put("endDate", checkStringFieldIsEmpty(simpleDateFormat.format(businessTrip.getEndDate()), emptyString));

        emailService.sendEmail(user.getEmail(),
                messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerNotificationEmailCaption"),
                messages.getMessage(CreateTaskListener.class, "mail.createTaskListenerEmailTemplateNotificationsVacation"),
                mapParam);
    }

}