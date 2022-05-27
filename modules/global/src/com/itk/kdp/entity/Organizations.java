package com.itk.kdp.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_ORGANIZATIONS")
@Entity(name = "kdp_Organizations")
public class Organizations extends StandardEntity {
    private static final long serialVersionUID = -7946626287646661870L;

    @NotNull
    @Column(name = "SHORT_NAME", nullable = false)
    private String shortName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}