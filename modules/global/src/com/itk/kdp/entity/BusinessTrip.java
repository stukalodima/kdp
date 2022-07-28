package com.itk.kdp.entity;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.Messages;
import com.itk.kdp.base.itk.StandardEntityITK;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Table(name = "KDP_BUSINESS_TRIP")
@Entity(name = "kdp_BusinessTrip")
@NamePattern("#getCaption| number,onDate")
public class BusinessTrip extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = 5104939189800091445L;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "ON_DATE")
    private Date onDate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEES_ID")
    private Employees employee;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organizations organization;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Departments department;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_ID")
    private Position position;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "DETAILS")
    private String details;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURPOSE_ID")
    private Purpose purpose;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSPORT_ID")
    private Transport transport;

    @Column(name = "HOTEL")
    private Boolean hotel;

    @Column(name = "VISA")
    private Boolean visa;

    @Column(name = "ANALYTICS")
    private String analytics;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "START_PLACE")
    private String startPlace;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "PAY_CENTER")
    private String payCenter;

    @Column(name = "IS_BUDGET")
    private String isBudget;

    @Column(name = "BUDGET")
    private String budgetTrip;

    @Column(name = "COMMENT")
    private String comment;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "businessTrip")
    private List<BusinessTripFiles> documents;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    private Employees author;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PLAIN")
    private Boolean plainTransport;

    @Column(name = "TRAIN")
    private Boolean trainTransport;

    @Column(name = "BUS")
    private Boolean busTransport;

    @Column(name = "AUTO_COMPANY")
    private Boolean autoCompanyTransport;

    @Column(name = "AUTO_SELF")
    private Boolean autoSelfTransport;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_INSTANCE_ID")
    private ProcInstance procInstance;

    public Boolean getPlainTransport() {
        return plainTransport;
    }

    public void setPlainTransport(Boolean plainTransport) {
        this.plainTransport = plainTransport;
    }

    public Boolean getTrainTransport() {
        return trainTransport;
    }

    public void setTrainTransport(Boolean trainTransport) {
        this.trainTransport = trainTransport;
    }

    public Boolean getBusTransport() {
        return busTransport;
    }

    public void setBusTransport(Boolean busTransport) {
        this.busTransport = busTransport;
    }

    public Boolean getAutoCompanyTransport() {
        return autoCompanyTransport;
    }

    public void setAutoCompanyTransport(Boolean autoCompanyTransport) {
        this.autoCompanyTransport = autoCompanyTransport;
    }

    public Boolean getAutoSelfTransport() {
        return autoSelfTransport;
    }

    public void setAutoSelfTransport(Boolean autoSelfTransport) {
        this.autoSelfTransport = autoSelfTransport;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Boolean getHotel() {
        return hotel;
    }

    public void setHotel(Boolean hotel) {
        this.hotel = hotel;
    }

    public Boolean getVisa() {
        return visa;
    }

    public void setVisa(Boolean visa) {
        this.visa = visa;
    }

    public String getAnalytics() {
        return analytics;
    }

    public void setAnalytics(String analytics) {
        this.analytics = analytics;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public PayCenterEnum getPayCenter() {
        return payCenter == null ? null : PayCenterEnum.fromId(payCenter);
    }

    public void setPayCenter(PayCenterEnum payCenter) {
        this.payCenter = payCenter == null ? null : payCenter.getId();
    }

    public String getIsBudget() {
        return isBudget;
    }

    public void setIsBudget(String isBudget) {
        this.isBudget = isBudget;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Employees getAuthor() {
        return author;
    }

    public void setAuthor(Employees author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBudgetTrip() {
        return budgetTrip;
    }

    public void setBudgetTrip(String budgetTrip) {
        this.budgetTrip = budgetTrip;
    }

    public Organizations getOrganization() {
        return organization;
    }

    public void setOrganization(Organizations organization) {
        this.organization = organization;
    }

    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ProcInstance getProcInstance() {
        return procInstance;
    }

    public void setProcInstance(ProcInstance procInstance) {
        this.procInstance = procInstance;
    }

    public List<BusinessTripFiles> getDocuments() {
        return documents;
    }

    public void setDocuments(List<BusinessTripFiles> documents) {
        this.documents = documents;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    @Override
    public String getCaption() {
        Messages messages = AppBeans.get(Messages.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return number +
                " " +
                messages.getMainMessage("entityCaption.dateFrom") +
                " " +
                simpleDateFormat.format(onDate);
    }
}