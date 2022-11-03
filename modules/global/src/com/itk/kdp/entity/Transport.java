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
import javax.validation.constraints.NotNull;

@Table(name = "KDP_TRANSPORT")
@Entity(name = "kdp_Transport")
@NamePattern("#getCaption | nameUa, nameEn, nameRu")
public class Transport extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = -3424446988148684893L;

    @MetaProperty
    @Transient
    private String name;

    @NotNull
    @Column(name = "NAME_UA")
    private String nameUa;

    @NotNull
    @Column(name = "NAME_EN")
    private String nameEn;

    @NotNull
    @Column(name = "NAME_RU")
    private String nameRu;


    @Column(name = "ACTIVE")
    private Boolean active;

    public String getName() {
        return getCaption();
    }

    public void setName(String name) {
        this.name = getCaption();
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String getCaption() {
        return MessageHelperITK.getCaption(nameUa, nameEn, nameRu);
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }
}