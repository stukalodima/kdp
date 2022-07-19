package com.itk.kdp.entity;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.core.global.validation.groups.UiCrossFieldChecks;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.service.EmployeeOrganizationService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Table(name = "KDP_VACATION_REQUEST")
@Entity(name = "kdp_VacationRequest")
@NamePattern("%s %s|applicationNumber,applicationDate")
@EventDate(groups = UiCrossFieldChecks.class)
public class VacationRequest extends StandardEntity {
    private static final long serialVersionUID = 379968912838652266L;

    @NotNull
    @Column(name = "APPLICATION_NUMBER", nullable = false)
    private Integer applicationNumber;


    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "APPLICATION_DATE")
    private Date applicationDate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EMPLOYEES_ID")
  //@OnDelete(DeletePolicy.UNLINK)
  //@OnDeleteInverse(DeletePolicy.DENY)
    private Employees employee;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPANY_ID")
    //@OnDelete(DeletePolicy.DENY)
    //@OnDeleteInverse(DeletePolicy.UNLINK)
   private Organizations company;


    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENTS_ID")
//    @OnDelete(DeletePolicy.DENY)
//    @OnDeleteInverse(DeletePolicy.UNLINK)
    private Departments department;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_ID")
//    @OnDelete(DeletePolicy.DENY)
//    @OnDeleteInverse(DeletePolicy.UNLINK)
    private Position position;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @NotNull
//    @OnDeleteInverse(DeletePolicy.UNLINK)
//    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COORDINATOR_ID")
    private Employees coordinator;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
//    @OnDeleteInverse(DeletePolicy.UNLINK)
//    @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VACATION_TYPE_ID")
    private VacationType vacationType;

    @Column(name = "REMAINING_VACATION_DAYS")
    private Integer remainingVacationDays;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM")
    private Date dateFrom;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_BY")
    private Date dateBy;

    @Column(name = "VACATION_DAYS")
    private Integer vacationDays;

//    @Column(name = "INFORMATION_FOR_CONSULTATIONS")
//    private String informationForConsultations;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "CONSENT_TO_BILLING")
    private Boolean consentToBilling;

    @Composition
//    @OnDeleteInverse(DeletePolicy.UNLINK)
//    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_ID")
    private FileDescriptor document;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
//    @OnDeleteInverse(DeletePolicy.UNLINK)
   // @OnDelete(DeletePolicy.DENY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INITIATOR_ID")
    private User initiator;

    @Column(name = "UPLOAD_TO_1C")
    private Boolean uploadTo1C;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "COMMENT")
    private String comment;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_INSTANCE_ID")
    private ProcInstance procInstance;
    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "businessTrip")
    private List<VacationRequestFiles> documents;
    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Organizations getCompany() {
        return company;
    }

    public void setCompany(Organizations company) {
        this.company = company;
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


    public VacationType getVacationType() {
        return vacationType;
    }

    public void setVacationType(VacationType vacationType) {
        this.vacationType = vacationType;
    }

    public Integer getRemainingVacationDays() {
        return remainingVacationDays;
    }

    public void setRemainingVacationDays(Integer remainingVacationDays) {
        this.remainingVacationDays = remainingVacationDays;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateBy() {
        return dateBy;
    }

    public void setDateBy(Date dateBy) {
        this.dateBy = dateBy;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }
//
//    public String getInformationForConsultations() {
//        return informationForConsultations;
//    }

//    public void setInformationForConsultations(String informationForConsultations) {
//        this.informationForConsultations = informationForConsultations;
//    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(Integer applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Employees getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Employees coordinator) {
        this.coordinator = coordinator;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Boolean getConsentToBilling() {
        return consentToBilling;
    }

    public void setConsentToBilling(Boolean consentToBilling) {
        this.consentToBilling = consentToBilling;
    }

    public Boolean getUploadTo1C() {
        return uploadTo1C;
    }

    public void setUploadTo1C(Boolean uploadTo1C) {
        this.uploadTo1C = uploadTo1C;
    }

    public FileDescriptor getDocument() {
        return document;
    }

    public void setDocument(FileDescriptor document) {
        this.document = document;
    }

    @PostConstruct
    private void initEntity(Metadata metadata) {
        List<Employees> employees = new ArrayList<>();
        EmployeeOrganizationService EmployeeOrganizationService = AppBeans.get(EmployeeOrganizationService.class);
        initiator = EmployeeOrganizationService.getUser();
        employees = EmployeeOrganizationService.getEmployeeOrganization();
        if (employees.size() == 1) {
            employee = employees.get(0);
            if (!Objects.isNull(employee)) {
                company = employee.getCompany();
                department = employee.getDepartment();
                position = employee.getPosition();
                coordinator = employee.getManager();
            }
        }
        setApplicationDate(today());
        }

    public static  Date today() {
        TimeSource timeSource = AppBeans.get(TimeSource.class);
        return DateUtils.truncate(timeSource.currentTimestamp(), Calendar.DATE);
    }
    public ProcInstance getProcInstance() {
        return procInstance;
    }

    public void setProcInstance(ProcInstance procInstance) {
        this.procInstance = procInstance;
    }

    public List<VacationRequestFiles> getDocuments() {
        return documents;
    }

    public void setDocuments(List<VacationRequestFiles> documents) {
        this.documents = documents;
    }
}


