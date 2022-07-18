package com.itk.kdp.entity;

import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.List;

@Table(name = "KDP_ADDRESSING")
@Entity(name = "kdp_Addressing")
public class Addressing extends StandardEntity {
    private static final long serialVersionUID = -8862784569085070496L;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROC_DEFINITION_ID")
    private ProcDefinition procDefinition;

    @Column(name = "USE_COMPANY")
    private Boolean useCompany;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    private Organizations company;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "addressing")
    private List<AddressingDetail> addressingDetail;

    public ProcDefinition getProcDefinition() {
        return procDefinition;
    }

    public void setProcDefinition(ProcDefinition procDefinition) {
        this.procDefinition = procDefinition;
    }

    public Boolean getUseCompany() {
        return useCompany;
    }

    public void setUseCompany(Boolean useCompany) {
        this.useCompany = useCompany;
    }

    public Organizations getCompany() {
        return company;
    }

    public void setCompany(Organizations company) {
        this.company = company;
    }

    public List<AddressingDetail> getAddressingDetail() {
        return addressingDetail;
    }

    public void setAddressingDetail(List<AddressingDetail> addressingDetail) {
        this.addressingDetail = addressingDetail;
    }
}