package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_RECONCILIATION_STATUSES")
@Entity(name = "kdp_ReconciliationStatuses")
@NamePattern("%s|name")
public class ReconciliationStatuses extends StandardEntity {
    private static final long serialVersionUID = 5029341305099067974L;

    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}