package com.itk.kdp.service;

import com.itk.kdp.entity.OperatorAccessRequest;

public interface OperatorAccessService {
    String NAME = "kdp_OperatorAccessService";
    void sendEmailToServiceDesk(OperatorAccessRequest operatorAccessRequest);
}