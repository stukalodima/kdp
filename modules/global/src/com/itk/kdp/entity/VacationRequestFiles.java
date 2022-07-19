package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "KDP_VACATION_REQUEST_FILES")
@Entity(name = "kdp_VacationRequestFiles")
public class VacationRequestFiles extends StandardEntity {
    private static final long serialVersionUID = -4293731557749419446L;

    @Composition
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_ID")
    private FileDescriptor document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VacationRequest_ID")
    private VacationRequest vacationRequest;

    @Column(name = "COMMENT")
    private String comment;

    public FileDescriptor getDocument() {
        return document;
    }

    public void setDocument(FileDescriptor document) {
        this.document = document;
    }

    public VacationRequest getBusinessTrip() {
        return vacationRequest;
    }

    public void setBusinessTrip(VacationRequest vacationRequest) {
        this.vacationRequest = vacationRequest;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}