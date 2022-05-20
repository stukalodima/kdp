package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "KDP_COUNTRY")
@Entity(name = "kdp_Country")
@NamePattern("%s (%s)|name,codeNumeric")
public class Country extends StandardEntity {
    private static final long serialVersionUID = -41062591185194232L;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "CODE_NUMERIC", nullable = false, unique = true)
    private Integer codeNumeric;

    @Column(name = "CODE_ALPHA3", length = 3)
    private String codeAlpha3;

    @Column(name = "CODE_ALPHA2", length = 2)
    private String codeAlpha2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeAlpha3() {
        return codeAlpha3;
    }

    public void setCodeAlpha3(String codeAlpha3) {
        this.codeAlpha3 = codeAlpha3;
    }

    public String getCodeAlpha2() {
        return codeAlpha2;
    }

    public void setCodeAlpha2(String codeAlpha2) {
        this.codeAlpha2 = codeAlpha2;
    }

    public Integer getCodeNumeric() {
        return codeNumeric;
    }

    public void setCodeNumeric(Integer codeNumeric) {
        this.codeNumeric = codeNumeric;
    }
}