package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.itk.kdp.base.itk.StandardEntityITK;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "KDP_VACATION_BALANCE")
@Entity(name = "kdp_VacationBalance")
@NamePattern("#getCaption|employee, vacationType")
public class VacationBalance extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = 5770466913659009549L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup","open","clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employees employee;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup","open","clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VACATION_TYPE_ID")
    private VacationType vacationType;

    @NotNull
    @Column(name = "days")
    private Integer days;

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public VacationType getVacationType() {
        return vacationType;
    }

    public void setVacationType(VacationType vacationType) {
        this.vacationType = vacationType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public String getCaption() {
        return employee.getCaption(true) + " (" + vacationType.getCaption() + ")";
    }
}