package com.itk.kdp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PayCenterEnum implements EnumClass<String> {

    A("A"),
    B("B"),
    C("C");

    private final String id;

    PayCenterEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PayCenterEnum fromId(String id) {
        for (PayCenterEnum at : PayCenterEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}