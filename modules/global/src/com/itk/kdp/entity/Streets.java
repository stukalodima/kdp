package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_STREETS")
@Entity(name = "kdp_Streets")
@NamePattern("%s|name")
public class Streets extends StandardEntity {
    private static final long serialVersionUID = -6918093609555279204L;

    @NotNull
    @Column(name = "CODE", nullable = false, unique = true, length = 10)
    private String code;

    @NotNull
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "NOTE")
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}