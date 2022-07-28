package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.itk.kdp.base.itk.StandardEntityITK;
import com.itk.kdp.entity.helper.MessageHelperITK;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_VACATION_TYPE")
@Entity(name = "kdp_VacationType")
@NamePattern("#getCaption|nameUa,nameEn,nameRu")
public class VacationType extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = 6590582644221728179L;
    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

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

    @Column(name = "VACATION_TYPE_1C")
    private String vacationType1c;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return getCaption();
    }

    public void setName(String name) {
        this.name = getCaption();
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

    @Override
    public String getCaption() {
        return MessageHelperITK.getCaption(nameUa, nameEn, nameRu);
    }

    public String getVacationType1c() {
        return vacationType1c;
    }

    public Long generateNewCode() {
        UniqueNumbersService numbersService = AppBeans.get(UniqueNumbersService.class);
        return numbersService.getNextNumber("vacationType");
    }

    public void setVacationType1c(String vacationType1c) {
        this.vacationType1c = vacationType1c;
    }
}