package com.itk.kdp.service;

import com.itk.kdp.entity.Organizations;

import java.io.IOException;

public interface CompanyService {
    String NAME = "kdp_CompanyService";

    void getCompanyListFromExternal() throws IOException;
    Organizations getCompanyByCode(String code);
    Organizations getCompanyById(String id);

}