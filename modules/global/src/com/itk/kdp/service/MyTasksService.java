package com.itk.kdp.service;

public interface MyTasksService {
    String NAME = "kdp_MyTasksService";

    long countTasksForCurrentUser();
}