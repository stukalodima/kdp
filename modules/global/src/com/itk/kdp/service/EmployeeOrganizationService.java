package com.itk.kdp.service;

import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.entity.Employees;

import java.util.List;

public interface EmployeeOrganizationService {
    String NAME = "kdp_EmployeeOrganizationService";

    List<Employees> getEmployeeOrganization();
    User getUser();
}