package com.itk.kdp.service;

import java.io.IOException;

public interface RestClientService {
    String NAME = "kdp_RestClientService";

    String callGetMethod(String restServiceUrl) throws IOException;
    String callGetMethod(String restServiceUrl, boolean withAuth) throws IOException;
}