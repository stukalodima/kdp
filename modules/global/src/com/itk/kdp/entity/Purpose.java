package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "KDP_PURPOSE")
@Entity(name = "kdp_Purpose")
@NamePattern("%s |nameUa")
public class Purpose extends StandardEntity {
    private static final long serialVersionUID = -93324234198195206L;

    @Column(name = "NAME")
    private String nameUa;

    @Column(name = "NAME_RU")
    private String nameRu;

    @Column(name = "NAME_EN")
    private String nameEn;

    @Column(name = "ACTIVE")
    private Boolean active;

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String name) {
        this.nameUa = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}