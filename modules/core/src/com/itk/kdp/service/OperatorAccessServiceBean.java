package com.itk.kdp.service;

import com.haulmont.cuba.core.global.Messages;
import com.itk.kdp.config.ServiceDeskConfig;
import com.itk.kdp.entity.OperatorAccessRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service(OperatorAccessService.NAME)
public class OperatorAccessServiceBean implements OperatorAccessService {

    @Inject
    private Messages messages;
    @Inject
    private ServiceDeskConfig serviceDeskConfig;
    @Inject
    private EmailService emailService;

    @Override
    public void sendEmailToServiceDesk(OperatorAccessRequest operatorAccessRequest) {
        Map<String, Serializable> mapParam = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        mapParam.put("systemName", messages.getMessage(OperatorAccessServiceBean.class, "mail.systemName"));
        mapParam.put("author", messages.getMessage(OperatorAccessServiceBean.class, "mail.author"));
        mapParam.put("userName", operatorAccessRequest.getAuthor().getName());
        mapParam.put("textUser", messages.getMessage(OperatorAccessServiceBean.class, "mail.textUser"));
        mapParam.put("operator", operatorAccessRequest.getOperator().getCaption());
        mapParam.put("moveTo", messages.getMessage(OperatorAccessServiceBean.class, "mail.moveTo"));
        mapParam.put("employee", operatorAccessRequest.getEmployee().getCaption());
        mapParam.put("periodFrom", messages.getMessage(OperatorAccessServiceBean.class, "mail.periodFrom"));
        mapParam.put("startDate", simpleDateFormat.format(operatorAccessRequest.getStartDate()));
        mapParam.put("periodTo", messages.getMessage(OperatorAccessServiceBean.class, "mail.periodTo"));
        mapParam.put("unit", messages.getMessage(OperatorAccessServiceBean.class, "mail.unit"));
        mapParam.put("comment", operatorAccessRequest.getComment());
        mapParam.put("endDate", operatorAccessRequest.getEndDate() == null ? messages.getMessage(OperatorAccessServiceBean.class, "mail.endDateText")
                : simpleDateFormat.format(operatorAccessRequest.getEndDate()));

        String mailServiceDesk = serviceDeskConfig.getServiceDeskMail();

        emailService.sendEmail(mailServiceDesk,
                messages.getMessage(OperatorAccessServiceBean.class, "mail.serviceDeskEmailTemplateOperatorAccessCaption"),
                messages.getMessage(OperatorAccessServiceBean.class, "mail.serviceDeskEmailTemplateOperatorAccess"),
                mapParam);
    }
}