package com.itk.kdp.core;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.itk.kdp.entity.BusinessTrip;
import com.itk.kdp.entity.VacationRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;

@Component(ApprovalHelperBean.NAME)
public class ApprovalHelperBean {
    public static final String NAME = "kdp_ApprovalHelperBean";
    @Inject
    private Persistence persistence;

    public void updateStateRegister(UUID entityId, String state, String type) {

        switch (type) {
            case "Trip": {
                try (Transaction tx = persistence.getTransaction()) {
                    BusinessTrip businessTrip = persistence.getEntityManager().find(BusinessTrip.class, entityId);
                    if (!Objects.isNull(businessTrip)) {
                        businessTrip.setStatus(state);
                    }
                    tx.commit();
                    break;
                }
            }
            case "Vacation": {
                try (Transaction tx = persistence.getTransaction()) {
                    VacationRequest vacationRequest = persistence.getEntityManager().find(VacationRequest.class, entityId);
                    if (!Objects.isNull(vacationRequest)) {
                        vacationRequest.setStatus(state);
                    }
                    tx.commit();
                    break;
                }
            }
        }
    }
}