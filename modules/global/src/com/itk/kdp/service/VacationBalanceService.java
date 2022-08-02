package com.itk.kdp.service;

import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.VacationBalance;
import com.itk.kdp.entity.VacationType;

import java.io.IOException;

public interface VacationBalanceService {
    String NAME = "kdp_VacationBalanceService";

    void getVacationBalanceListFromExternal() throws IOException;
    VacationBalance getVacationBalanceByEmployeeAndVacationType(Employees employee, VacationType vacationType);
}