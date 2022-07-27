package com.itk.kdp.service;

import com.itk.kdp.entity.Organizations;
import com.itk.kdp.entity.Position;

import java.io.IOException;

public interface PositionService {
    String NAME = "kdp_PositionService";

    void getPositionListFromExternal() throws IOException;
    Position getPositionByCode(String code);
    Position getPositionById(String id);
}