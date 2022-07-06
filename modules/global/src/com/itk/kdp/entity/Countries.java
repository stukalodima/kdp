package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.AppBeans;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_COUNTRIES")
@Entity(name = "kdp_Countries")
@NamePattern("%s|name")
public class Countries extends StandardEntity {
    private static final long serialVersionUID = 824192924430334148L;

    @NotNull
    @Column(name = "CODE", nullable = false, unique = true)
    @Max(message = "{msg://kdp_Countries.code.validation.Max}", value = 999)
    private Integer code;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}