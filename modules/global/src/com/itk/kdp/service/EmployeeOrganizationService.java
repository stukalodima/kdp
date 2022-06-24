package com.itk.kdp.service;

import com.itk.kdp.entity.Employees;

import java.util.List;

public interface EmployeeOrganizationService {
    String NAME = "kdp_EmployeeOrganizationService";

    List<Employees> getEmployeeOrganization();
}