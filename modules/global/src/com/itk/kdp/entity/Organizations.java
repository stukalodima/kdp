package com.itk.kdp.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Table(name = "KDP_ORGANIZATIONS")
@Entity(name = "kdp_Organizations")
public class Organizations extends StandardEntity {
    private static final long serialVersionUID = -7946626287646661870L;

    @NotNull
    @Column(name = "CODE", nullable = false, length = 9)
    private String code;

    @NotNull
    @Column(name = "PREFIX", nullable = false, length = 3)
    private String prefix;

    @NotNull
    @Column(name = "SHORT_NAME", nullable = false)
    private String shortName;

    @NotNull
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "ORGANIZATIONS_EN", nullable = false)
    private String organizationsEn;

    @NotNull
    @Column(name = "ORGANIZATIONS_UA", nullable = false)
    private String organizationsUa;

    @NotNull
    @Column(name = "ORGANIZATIONS_RU", nullable = false)
    private String organizationsRu;

    @NotNull
    @Column(name = "CODE_OCPO", nullable = false, length = 12)
    private String codeRegistration;

    @Column(name = "DATE_REGISTRATION")
    @Temporal(TemporalType.DATE)
    private Date dateRegistration;

    @Column(name = "COUNTRY_REGISTRATION")
    private String countryRegistration;

    @Column(name = "ORGANIZATIONS1C_ID")
    private UUID organizations1cId;

    @Column(name = "ENTITY")
    private Boolean entity;

    @Column(name = "ACTIVE")
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UUID getOrganizations1cId() {
        return organizations1cId;
    }

    public void setOrganizations1cId(UUID organizations1cId) {
        this.organizations1cId = organizations1cId;
    }

    public String getOrganizationsRu() {
        return organizationsRu;
    }

    public void setOrganizationsRu(String organizationsRu) {
        this.organizationsRu = organizationsRu;
    }

    public String getOrganizationsUa() {
        return organizationsUa;
    }

    public void setOrganizationsUa(String organizationsUa) {
        this.organizationsUa = organizationsUa;
    }

    public String getOrganizationsEn() {
        return organizationsEn;
    }

    public void setOrganizationsEn(String organizationsEn) {
        this.organizationsEn = organizationsEn;
    }

    public String getCodeRegistration() {
        return codeRegistration;
    }

    public void setCodeRegistration(String codeRegistration) {
        this.codeRegistration = codeRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public Boolean getEntity() {
        return entity;
    }

    public void setEntity(Boolean entity) {
        this.entity = entity;
    }

    public String getCountryRegistration() {
        return countryRegistration;
    }

    public void setCountryRegistration(String countryRegistration) {
        this.countryRegistration = countryRegistration;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}