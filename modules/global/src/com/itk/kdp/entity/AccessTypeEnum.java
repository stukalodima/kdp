package com.itk.kdp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum AccessTypeEnum implements EnumClass<String> {

    INTERNET("INTERNET"),
    USB("USB"),
    VPN("VPN"),
    NETWORK("NETWORK"),
    EMAIL("EMAIL"),
    OTHER_PO("OTHER_PO");

    private String id;

    AccessTypeEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static AccessTypeEnum fromId(String id) {
        for (AccessTypeEnum at : AccessTypeEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}