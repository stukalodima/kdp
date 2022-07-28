package com.itk.kdp.service;

import com.itk.kdp.entity.Countries;
import com.itk.kdp.entity.Organizations;

import java.io.IOException;

public interface CountryService {
    String NAME = "kdp_CountryService";

    void getCountryListFromExternal() throws IOException;
    Countries getCountryByCode(String code);
    Countries getCountryById(String id);
}