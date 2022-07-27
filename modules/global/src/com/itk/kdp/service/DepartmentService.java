package com.itk.kdp.service;

import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Position;

import java.io.IOException;

public interface DepartmentService {
    String NAME = "kdp_DepartmentService";

    void getDepartmentListFromExternal() throws IOException;
    Departments getDepartmentByCode(String code);
    Departments getDepartmentById(String id);
}