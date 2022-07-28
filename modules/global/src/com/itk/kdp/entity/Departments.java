package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.itk.kdp.base.itk.StandardEntityITK;
import com.itk.kdp.entity.helper.MessageHelperITK;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Table(name = "KDP_DEPARTMENTS")
@Entity(name = "kdp_Departments")
@NamePattern("#getCaption|departmentUa, departmentEn, departmentRu")
public class Departments extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = -179585123169757061L;

    @NotNull
    @Column(name = "CODE")
    private Long code;

    @MetaProperty
    @Transient
    private String name;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @OnDeleteInverse(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVAL_MANAGER_ID")
    @OnDelete(DeletePolicy.DENY)
    private Employees approvalManager;

    @NotNull
    @Column(name = "DEPARTMENT_EN")
    private String departmentEn;

    @NotNull
    @Column(name = "DEPARTMENT_UA")
    private String departmentUa;

    @NotNull
    @Column(name = "DEPARTMENT_RU")
    private String departmentRu;

    @Column(name = "DEPARTMENT_1C_ID")
    private String department1cId;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organizations organizationsId;

    @OnDeleteInverse(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_ID_ID")
    @OnDelete(DeletePolicy.UNLINK)
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    private Departments parentId;

    @OnDeleteInverse(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID_ID")
    @OnDelete(DeletePolicy.DENY)
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    private Employees managerId;

    public void setManagerId(Employees managerId) {
        this.managerId = managerId;
    }

    public Employees getManagerId() {
        return managerId;
    }

    public void setApprovalManager(Employees approvalManager) {
        this.approvalManager = approvalManager;
    }

    public Employees getApprovalManager() {
        return approvalManager;
    }

    public void setParentId(Departments parentId) {
        this.parentId = parentId;
    }

    public Departments getParentId() {
        return parentId;
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

    public Organizations getOrganizationsId() {
        return organizationsId;
    }

    public void setOrganizationsId(Organizations organizationsId) {
        this.organizationsId = organizationsId;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return getCaption();
    }

    public void setName(String name) {
        this.name = getCaption();
    }

    public Long generateNewCode() {
        UniqueNumbersService numbersService = AppBeans.get(UniqueNumbersService.class);
        return numbersService.getNextNumber("department");
    }

    public String getDepartment1cId() {
        return department1cId;
    }

    public void setDepartment1cId(String department1cId) {
        this.department1cId = department1cId;
    }

    public String getCaption() {
        return MessageHelperITK.getCaption(departmentUa, departmentEn, departmentRu);
    }
}