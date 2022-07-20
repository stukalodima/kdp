package com.itk.kdp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ProcEntityEnum implements EnumClass<String> {

    BUSINESS_TRIP("BUSINESS_TRIP"),
    VACATION_REQUEST("VACATION_REQUEST");

    private final String id;

    ProcEntityEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ProcEntityEnum fromId(String id) {
        for (ProcEntityEnum at : ProcEntityEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}