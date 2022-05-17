package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_REGION")
@Entity(name = "kdp_Region")
@NamePattern("%s|nameRegion")
public class Region extends StandardEntity {
    private static final long serialVersionUID = -404804251140645969L;

    @NotNull
    @Column(name = "NAME_REGION", nullable = false, unique = true)
    private String nameRegion;

    public String getNameRegion() {
        return nameRegion;
    }

    public void setNameRegion(String nameRegion) {
        this.nameRegion = nameRegion;
    }
}