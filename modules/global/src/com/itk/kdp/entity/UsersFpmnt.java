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

@Table(name = "KDP_USERS_FPMNT")
@Entity(name = "kdp_UsersFpmnt")
@NamePattern("#getCaption|nameUa,nameEn,nameRu")
public class UsersFpmnt extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = -1869913298654468537L;

    @MetaProperty
    @Transient
    private String name;

    @Column(name = "NAME_UA")
    private String nameUa;

    @Column(name = "NAME_En")
    private String nameEn;

    @Column(name = "NAME_RU")
    private String nameRu;

    @Column(name = "USER_TYPE")
    private String userType;

    public String getName() {
        return getCaption();
    }

    public void setName(String name) {
        this.name = getCaption();
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUA) {
        this.nameUa = nameUA;
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

    public UserTypeFpmntEnum getUserType() {
        return userType == null ? null : UserTypeFpmntEnum.fromId(userType);
    }

    public void setUserType(UserTypeFpmntEnum userType) {
        this.userType = userType == null ? null : userType.getId();
    }

    @Override
    public String getCaption() {
        return MessageHelperITK.getCaption(nameUa, nameEn, nameRu);
    }
}