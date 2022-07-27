package com.itk.kdp.service;

import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;

import java.io.IOException;

public interface EmployeeService {
    String NAME = "kdp_EmployeeService";
    void getEmployeeListFromExternal() throws IOException;
    Employees getEmployeeByCode(String code);
    Employees getEmployeeById(String id);
    Employees getEmployeeByUser(User user);
}