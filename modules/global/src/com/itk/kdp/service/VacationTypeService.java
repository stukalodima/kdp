package com.itk.kdp.service;

import com.itk.kdp.entity.VacationType;

import java.io.IOException;

public interface VacationTypeService {
    String NAME = "kdp_VacationTypeService";

    void getVacationTypeListFromExternal() throws IOException;
    VacationType getVacationTypeByCode(String code);
    VacationType getVacationTypeById(String id);
}