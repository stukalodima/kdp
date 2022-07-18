package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "KDP_PURPOSE")
@Entity(name = "kdp_Purpose")
@NamePattern("%s | name")
public class Purpose extends StandardEntity {
    private static final long serialVersionUID = -93324234198195206L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVE")
    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}