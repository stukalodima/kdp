package com.itk.kdp.service;

import com.itk.kdp.entity.Departments;

import java.util.List;

public interface GetDepartmensService {
    String NAME = "kdp_GetDepartmensService";
    List<Departments>  getDepartmentsFilter(Departments departments);

}