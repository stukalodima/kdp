package com.itk.kdp.entity;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.*;
import java.util.Date;

@Table(name = "KDP_ACCESS_REQUEST")
@Entity(name = "kdp_AccessRequest")
@NamePattern("%s (%s) | employees, accessType")
public class AccessRequest extends StandardEntity {
    private static final long serialVersionUID = -3733440090475129273L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employees employees;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    private Organizations company;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Employees manager;

    @Column(name = "ACCESS_TYPE")
    private String accessType;

    @Column(name = "TECH_INFO")
    private String techInfo;

    @Column(name = "BUSINESS_INFO")
    private String businessInfo;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Lookup(type = LookupType.DROPDOWN,actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_INSTANCE")
    private ProcInstance procInstance;

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Organizations getCompany() {
        return company;
    }

    public void setCompany(Organizations company) {
        this.company = company;
    }

    public Employees getManager() {
        return manager;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public AccessTypeEnum getAccessType() {
        return accessType == null ? null : AccessTypeEnum.fromId(accessType);
    }

    public void setAccessType(AccessTypeEnum accessType) {
        this.accessType = accessType == null ? null : accessType.getId();
    }

    public String getTechInfo() {
        return techInfo;
    }

    public void setTechInfo(String techInfo) {
        this.techInfo = techInfo;
    }

    public String getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(String businessInfo) {
        this.businessInfo = businessInfo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ProcInstance getProcInstance() {
        return procInstance;
    }

    public void setProcInstance(ProcInstance procInstance) {
        this.procInstance = procInstance;
    }
}