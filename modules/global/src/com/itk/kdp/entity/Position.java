package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.itk.kdp.base.itk.StandardEntityITK;
import com.itk.kdp.entity.helper.MessageHelperITK;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_POSITION")
@Entity(name = "kdp_Position")
@NamePattern("#getCaption|positionUa,positionEn,positionRu")
public class Position extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = 6282166328827621264L;

    @MetaProperty
    @Transient
    private String name;

    @NotNull
    @Column(name = "POSITION_EN")
    private String positionEn;

    @NotNull
    @Column(name = "POSITION_UA")
    private String positionUa;

    @NotNull
    @Column(name = "POSITION_RU")
    private String positionRu;

    @Column(name = "POSITION_1C_ID")
    private String position1cId;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORGANIZATIONS_ID_ID")
    private Organizations organizationsId;

    public Organizations getOrganizationsId() {
        return organizationsId;
    }

    public void setOrganizationsId(Organizations organizationsId) {
        this.organizationsId = organizationsId;
    }

    public void setPositionRu(String positionRu) {
        this.positionRu = positionRu;
    }

    public String getPositionRu() {
        return positionRu;
    }

    public String getPositionUa() {
        return positionUa;
    }

    public void setPositionUa(String positionUa) {
        this.positionUa = positionUa;
    }

    public String getPositionEn() {
        return positionEn;
    }

    public void setPositionEn(String positionEn) {
        this.positionEn = positionEn;
    }

    public String getPosition1cId() {
        return position1cId;
    }

    public void setPosition1cId(String position1cId) {
        this.position1cId = position1cId;
    }

    public String getName() {
        return getCaption();
    }

    public void setName(String name) {
        this.name = getCaption();
    }

    @Override
    public String getCaption() {
        return MessageHelperITK.getCaption(positionUa, positionEn, positionRu);
    }
}