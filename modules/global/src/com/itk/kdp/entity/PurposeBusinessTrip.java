package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_PURPOSE_BUSINESS_TRIP")
@Entity(name = "kdp_PurposeBusinessTrip")
@NamePattern("%s|name")
public class PurposeBusinessTrip extends StandardEntity {
    private static final long serialVersionUID = -7904895556029033863L;

    @Column(name = "NAME", nullable = false, unique = true)
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}