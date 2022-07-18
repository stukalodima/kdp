package com.itk.kdp.entity;

import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;

@Table(name = "KDP_ADDRESSING_DETAIL")
@Entity(name = "kdp_AddressingDetail")
public class AddressingDetail extends StandardEntity {
    private static final long serialVersionUID = -2196707147016825231L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROC_ROLE_ID")
    private ProcRole procRole;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "IS_INITIAL")
    private Boolean isInitial;

    @Column(name = "IS_NANAGER")
    private Boolean isManager;

    @Column(name = "AUTO")
    private Boolean auto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADDRESSING_ID")
    private Addressing addressing;

    public ProcRole getProcRole() {
        return procRole;
    }

    public void setProcRole(ProcRole procRole) {
        this.procRole = procRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public Addressing getAddressing() {
        return addressing;
    }

    public void setAddressing(Addressing addressing) {
        this.addressing = addressing;
    }

    public Boolean getIsInitial() {
        return isInitial;
    }

    public void setIsInitial(Boolean initial) {
        isInitial = initial;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(Boolean manager) {
        isManager = manager;
    }
}