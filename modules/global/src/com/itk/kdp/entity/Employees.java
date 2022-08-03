package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.base.itk.StandardEntityITK;
import com.itk.kdp.entity.helper.MessageHelperITK;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Table(name = "KDP_EMPLOYEES")
@Entity(name = "kdp_Employees")
@NamePattern("#getCaption|surnameUa, nameUa,surnameEn, nameEn,surnameRu, nameRu, company")
public class Employees extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = -3233447108705407573L;

    @MetaProperty
    @Transient
    private String fio;

    @NotNull
    @Column(name = "SURNAME_UA")
    private String surnameUa;

    @NotNull
    @Column(name = "NAME_UA")
    private String nameUa;

    @NotNull
    @Column(name = "MIDDLE_NAME_UA")
    private String middleNameUa;

    @NotNull
    @Column(name = "SURNAME_RU")
    private String surnameRu;

    @NotNull
    @Column(name = "NAME_RU")
    private String nameRu;

    @NotNull
    @Column(name = "MIDDLE_NAME_RU")
    private String middleNameRu;

    @NotNull
    @Column(name = "SURNAME_EN")
    private String surnameEn;

    @NotNull
    @Column(name = "NAME_EN")
    private String nameEn;

    @Column(name = "MIDDLE_NAME_EN")
    private String middleNameEn;

    @NotNull
    @Column(name = "WORK_EMAIL")
    private String workEmail;

    @Column(name = "OTHER_EMAIL")
    private String otherEmail;

    @Column(name = "WORK_PHONE")
    private String workPhone;

    @NotNull
    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    @OnDelete(DeletePolicy.DENY)
    @OnDeleteInverse(DeletePolicy.UNLINK)
    private Organizations company;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Departments department;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_ID")
    private Position position;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Employees manager;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVAL_MANAGER_ID")
    private Employees approvalManager;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VACATION_MANAGER_ID")
    private Employees vacationManager;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPLOYMENT_DATE")
    private Date employmentDate;

    @Column(name = "LOGIN_NAME")
    private String loginName;

    @Column(name = "FORM_EMPLOYMENT")
    private Boolean formEmployment;

    @Column(name = "EMPLOYEE_1C_ID")
    private String employee1cId;

    @Composition
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHOTO_ID")
    private FileDescriptor photo;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public String getEmployee1cId() {
        return employee1cId;
    }

    public void setEmployee1cId(String employee1cId) {
        this.employee1cId = employee1cId;
    }

    public FileDescriptor getPhoto() {
        return photo;
    }

    public void setPhoto(FileDescriptor photo) {
        this.photo = photo;
    }

    public Boolean getFormEmployment() {
        return formEmployment;
    }

    public void setFormEmployment(Boolean formEmployment) {
        this.formEmployment = formEmployment;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Employees getManager() {
        return manager;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
    }

    public Organizations getCompany() {
        return company;
    }

    public void setCompany(Organizations company) {
        this.company = company;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getOtherEmail() {
        return otherEmail;
    }

    public void setOtherEmail(String otherEmail) {
        this.otherEmail = otherEmail;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getMiddleNameEn() {
        return middleNameEn;
    }

    public void setMiddleNameEn(String middleNameEn) {
        this.middleNameEn = middleNameEn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getSurnameEn() {
        return surnameEn;
    }

    public void setSurnameEn(String surnameEn) {
        this.surnameEn = surnameEn;
    }

    public String getMiddleNameRu() {
        return middleNameRu;
    }

    public void setMiddleNameRu(String middleNameRu) {
        this.middleNameRu = middleNameRu;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getSurnameRu() {
        return surnameRu;
    }

    public void setSurnameRu(String surnameRu) {
        this.surnameRu = surnameRu;
    }

    public String getMiddleNameUa() {
        return middleNameUa;
    }

    public void setMiddleNameUa(String middleNameUa) {
        this.middleNameUa = middleNameUa;
    }

    public String getSurnameUa() {
        return surnameUa;
    }

    public void setSurnameUa(String surnameUa) {
        this.surnameUa = surnameUa;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFio() {
        return getCaption();
    }

    public void setFio(String fio) {
        this.fio = getCaption();
    }

    @Override
    public String getCaption() {
        return getCaption(true);
    }

    public String getCaption(Boolean withCompany) {
        return MessageHelperITK.getCaption(getFioUa(withCompany), getFioEn(withCompany), getFioRu(withCompany));
    }

    private String getFioRu(Boolean withCompany) {
        return getFioByLoc(surnameRu, nameRu, withCompany);
    }

    private String getFioEn(Boolean withCompany) {
        return getFioByLoc(surnameEn, nameEn, withCompany);
    }

    private String getFioUa(Boolean withCompany) {
        return getFioByLoc(surnameUa, nameUa, withCompany);
    }

    private String getFioUa() {
        return getFioUa(true);
    }

    private String getFioEn() {
        return getFioEn(true);
    }

    private String getFioRu() {
        return getFioRu(true);
    }

    private String getFioByLoc(String surname, String name) {
        return getFioByLoc(surname, name, true);
    }

    private String getFioByLoc(String surname, String name, Boolean withCompany) {
        if (!Objects.isNull(surname) && !Objects.isNull(name)) {
            return surname +
                    " " +
                    name +
                    (Boolean.TRUE.equals(withCompany) ? " (" + company.getCaption() + ")" : "");
        } else {
            return "<>";
        }
    }

    public Employees getApprovalManager() {
        return approvalManager;
    }

    public void setApprovalManager(Employees approvalManager) {
        this.approvalManager = approvalManager;
    }

    public Employees getVacationManager() {
        return vacationManager;
    }

    public void setVacationManager(Employees vacationManager) {
        this.vacationManager = vacationManager;
    }
}