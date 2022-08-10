package com.itk.kdp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum UserTypeFpmntEnum implements EnumClass<String> {

    OPERATOR("OPERATOR"),
    CONTROLLER("CONTROLLER");

    private String id;

    UserTypeFpmntEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static UserTypeFpmntEnum fromId(String id) {
        for (UserTypeFpmntEnum at : UserTypeFpmntEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}