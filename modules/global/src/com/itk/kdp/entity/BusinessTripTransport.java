package com.itk.kdp.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.itk.kdp.base.itk.StandardEntityITK;

import javax.persistence.*;

@Table(name = "KDP_BUSINESS_TRIP_TRANSPORT")
@Entity(name = "kdp_BusinessTripTransport")
@NamePattern("#getCaption|transport, businessTrip")
public class BusinessTripTransport extends StandardEntity implements StandardEntityITK {
    private static final long serialVersionUID = 179117255838845028L;

    @Column(name = "CHECK_TRANSPORT")
    private Boolean checkTransport;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSPORT_ID")
    private Transport transport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS_TRIP_ID")
    private BusinessTrip businessTrip;

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public BusinessTrip getBusinessTrip() {
        return businessTrip;
    }

    public void setBusinessTrip(BusinessTrip businessTrip) {
        this.businessTrip = businessTrip;
    }

    @Override
    public String getCaption() {
        return transport.getCaption() + "(" + businessTrip.getCaption() + ")";
    }

    public Boolean getCheckTransport() {
        return checkTransport;
    }

    public void setCheckTransport(Boolean checkTransport) {
        this.checkTransport = checkTransport;
    }
}