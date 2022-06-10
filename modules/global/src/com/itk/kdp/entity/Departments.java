package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Table(name = "KDP_DEPARTMENTS")
@Entity(name = "kdp_Departments")
@NamePattern("%s|name")

public class Departments extends StandardEntity {
    private static final long serialVersionUID = -179585123169757061L;

    @NotNull
    @Column(name = "CODE", nullable = false, unique = true)
    private Integer code;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "clear"})
    @OnDeleteInverse(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVAL_MANAGER_ID")
    private User approvalManager;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "DEPARTMENT_EN", nullable = false)
    private String departmentEn;

    @NotNull
    @Column(name = "DEPARTMENT_UA", nullable = false)
    private String departmentUa;

    @NotNull
    @Column(name = "DEPARTMENT_RU", nullable = false)
    private String departmentRu;

    @Column(name = "DEPARTMENT_ID")
    private UUID departmentId;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organizations companyId;

    @OnDeleteInverse(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_ID_ID")
    @OnDelete(DeletePolicy.UNLINK)
    private Departments pId;

    @OnDeleteInverse(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID_ID")
    private User managerId;

    public void setpId(Departments pId) {
        this.pId = pId;
    }

    public Departments getpId() {
        return pId;
    }

    public User getApprovalManager() {
        return approvalManager;
    }

    public void setApprovalManager(User approvalManager) {
        this.approvalManager = approvalManager;
    }

    public User getManagerId() {
        return managerId;
    }

    public void setManagerId(User managerId) {
        this.managerId = managerId;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentRu() {
        return departmentRu;
    }

    public void setDepartmentRu(String departmentRu) {
        this.departmentRu = departmentRu;
    }

    public String getDepartmentUa() {
        return departmentUa;
    }

    public void setDepartmentUa(String departmentUa) {
        this.departmentUa = departmentUa;
    }

    public String getDepartmentEn() {
        return departmentEn;
    }

    public void setDepartmentEn(String departmentEn) {
        this.departmentEn = departmentEn;
    }

    public Organizations getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Organizations companyId) {
        this.companyId = companyId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}