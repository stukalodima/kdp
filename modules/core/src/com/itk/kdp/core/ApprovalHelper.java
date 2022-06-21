package com.itk.kdp.core;

/*
import org.springframework.stereotype.Component;

@Component(ApprovalHelper.NAME)
public class ApprovalHelper {
    public static final String NAME = "kdp_ApprovalHelper";
}

package com.company.bpmdemo.core;
*/

import org.springframework.stereotype.Component;
import com.itk.kdp.entity.VacationRequest;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;

import javax.inject.Inject;
import java.util.UUID;

@Component(ApprovalHelper.NAME)
public class ApprovalHelper {
    public static final String NAME = "kdp_ApprovalHelper";

    @Inject
    private Persistence persistence;

    public void updateState(UUID entityId, String state) {
        try (Transaction tx = persistence.getTransaction()) {
            VacationRequest vacationRequest = persistence.getEntityManager().find(VacationRequest.class, entityId);
            if (vacationRequest != null) {
                vacationRequest.setState(state);
            }
            tx.commit();
        }
    }
}