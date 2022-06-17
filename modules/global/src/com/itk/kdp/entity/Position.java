package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_POSITION")
@Entity(name = "kdp_Position")
@NamePattern("%s|name")
public class Position extends StandardEntity {
    private static final long serialVersionUID = 6282166328827621264L;

    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "POSITION_EN", nullable = false)
    private String positionEn;

    @Column(name = "POSITION_UA", nullable = false)
    private String positionUa;

    @Column(name = "POSITION_RU", nullable = false)
    private String positionRu;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}