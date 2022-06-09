package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "KDP_POSITION")
@Entity(name = "kdp_Position")
@NamePattern("%s|name")
public class Position extends StandardEntity {
    private static final long serialVersionUID = 6282166328827621264L;

    @Column(name = "NAME")
    private String name;
    @Column(name = "POSITION_EN", nullable = false)
    private String positionEn;
    @Column(name = "POSITION_UA", nullable = false)
    private String positionUa;
    @Column(name = "POSITION_RU", nullable = false)
    private String positionRu;
    @Column(name = "POSITION_ID", nullable = false)
    private UUID positionId;
    @Column(name = "COMPANY_ID", nullable = false)
    private UUID companyId;

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public UUID getPositionId() {
        return positionId;
    }

    public void setPositionId(UUID positionId) {
        this.positionId = positionId;
    }

    public String getPositionRu() {
        return positionRu;
    }

    public void setPositionRu(String positionRu) {
        this.positionRu = positionRu;
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