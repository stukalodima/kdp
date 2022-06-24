package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "KDP_EMPLOYEES")
@Entity(name = "kdp_Employees")
@NamePattern("%s %s %s |surnameUa,nameUa, middleNameUa")
public class Employees extends StandardEntity {
    private static final long serialVersionUID = -3233447108705407573L;

    @NotNull
    @Column(name = "SURNAME_UA", nullable = false, length = 250)
    private String surnameUa;

    @NotNull
    @Column(name = "NAME_UA", nullable = false, length = 250)
    private String nameUa;

    @NotNull
    @Column(name = "MIDDLE_NAME_UA", nullable = false, length = 250)
    private String middleNameUa;

    @NotNull
    @Column(name = "SURNAME_RU", nullable = false, length = 250)
    private String surnameRu;

    @NotNull
    @Column(name = "NAME_RU", nullable = false, length = 250)
    private String nameRu;

    @NotNull
    @Column(name = "MIDDLE_NAME_RU", nullable = false, length = 250)
    private String middleNameRu;

    @NotNull
    @Column(name = "SURNAME_EN", nullable = false, length = 250)
    private String surnameEn;

    @NotNull
    @Column(name = "NAME_EN", nullable = false, length = 250)
    private String nameEn;

    @NotNull
    @Column(name = "MIDDLE_NAME_EN", nullable = false, length = 250)
    private String middleNameEn;

    @NotNull
    @Column(name = "WORK_EMAIL", nullable = false, length = 100)
    private String workEmail;

    @Column(name = "OTHER_EMAIL", length = 100)
    private String otherEmail;

    @Column(name = "WORK_PHONE", length = 15)
    @Length(message = "{msg://kdp_Employees.workPhone.validation.Length}", min = 13, max = 15)
    private String workPhone;

    @Column(name = "MOBILE_PHONE", length = 15)
    @Length(message = "{msg://kdp_Employees.mobilePhone.validation.Length}", min = 13, max = 15)
    private String mobilePhone;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPANY_ID")
    @OnDelete(DeletePolicy.DENY)
    @OnDeleteInverse(DeletePolicy.UNLINK)
    private Organizations company;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Departments department;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POSITION_ID")
    private Position position;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Employees manager;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Temporal(TemporalType.DATE)
    @Column(name = "EMPLOYMENT_DATE")
    private Date employmentDate;

    @NotNull
    @Column(name = "LOGIN_NAME", nullable = false, length = 250)
    private String loginName;

    @Column(name = "FORM_EMPLOYMENT")
    private Boolean formEmployment;

    @Composition
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHOTO_ID")
    private FileDescriptor photo;

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
}