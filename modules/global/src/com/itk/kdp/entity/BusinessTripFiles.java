package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "KDP_BUSINESS_TRIP_FILES")
@Entity(name = "kdp_BusinessTripFiles")
public class BusinessTripFiles extends StandardEntity {
    private static final long serialVersionUID = -5917140226219142925L;

    @Composition
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_ID")
    private FileDescriptor document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS_TRIP_ID")
    private BusinessTrip businessTrip;

    @Column(name = "COMMENT")
    private String comment;

    public FileDescriptor getDocument() {
        return document;
    }

    public void setDocument(FileDescriptor document) {
        this.document = document;
    }

    public BusinessTrip getBusinessTrip() {
        return businessTrip;
    }

    public void setBusinessTrip(BusinessTrip businessTrip) {
        this.businessTrip = businessTrip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}