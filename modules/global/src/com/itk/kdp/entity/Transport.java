package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "KDP_TRANSPORT")
@Entity(name = "kdp_Transport")
@NamePattern("%s | name")
public class Transport extends StandardEntity {
    private static final long serialVersionUID = -3424446988148684893L;

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