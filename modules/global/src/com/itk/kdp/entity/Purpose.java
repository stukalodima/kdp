package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.itk.kdp.base.itk.StandardEntityITK;
import com.itk.kdp.entity.helper.MessageHelperITK;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "KDP_PURPOSE")
@Entity(name = "kdp_Purpose")
@NamePattern("#getCaption |nameUa, nameEn, nameRu")
public class Purpose extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = -93324234198195206L;

    @MetaProperty
    @Transient
    private String name;

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

    @Override
    public String getCaption() {
        return MessageHelperITK.getCaption(nameUa,nameEn,nameRu);
    }

    public String getName() {
        return getCaption();
    }

    public void setName(String name) {
        this.name = getCaption();
    }
}