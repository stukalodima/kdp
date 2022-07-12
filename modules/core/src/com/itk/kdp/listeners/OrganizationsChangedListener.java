package com.itk.kdp.listeners;

import com.itk.kdp.entity.Organizations;

import java.util.UUID;

import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component("kdp_OrganizationsChangedListener")
public class OrganizationsChangedListener {

    @EventListener
    public void beforeCommit(EntityChangedEvent<Organizations, UUID> event) {
        
    }
}