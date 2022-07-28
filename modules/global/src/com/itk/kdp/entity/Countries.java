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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_COUNTRIES")
@Entity(name = "kdp_Countries")
@NamePattern("#getCaption|nameUa, nameEn, nameRu")
public class Countries extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = 824192924430334148L;

    @Column(name = "CODE")
    private Integer code;

    @MetaProperty
    @Transient
    private String name;

    @Column(name = "NAME_UA")
    private String nameUa;

    @Column(name = "NAME_EN")
    private String nameEn;

    @Column(name = "NAME_Ru")
    private String nameRu;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public String getName() {
        return getCaption();
    }

    public void setName(String name) {
        this.name = getCaption();
    }

    public String getCaption() {
        return MessageHelperITK.getCaption(nameUa, nameEn, nameRu);
    }
}