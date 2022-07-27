package com.itk.kdp.core;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.itk.kdp.entity.BusinessTrip;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;

@Component(ApprovalHelperBean.NAME)
public class ApprovalHelperBean {
    public static final String NAME = "kdp_ApprovalHelperBean";
    @Inject
    private Persistence persistence;

    public void updateStateRegister(UUID entityId, String state) {
        try (Transaction tx = persistence.getTransaction()) {
            BusinessTrip businessTrip = persistence.getEntityManager().find(BusinessTrip.class, entityId);
            if (!Objects.isNull(businessTrip)) {
                businessTrip.setStatus(state);
            }
            tx.commit();
        }
    }
}