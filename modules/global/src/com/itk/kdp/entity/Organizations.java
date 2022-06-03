package com.itk.kdp.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @Column(name = "DATE_REGISTRATION")
    private LocalDate dateRegistration;

    @Column(name = "COUNTRY_REGISTRATION")
    private String countryRegistration;

    @Column(name = "ENTITY")
    private Boolean entity;

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

    public LocalDate getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
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