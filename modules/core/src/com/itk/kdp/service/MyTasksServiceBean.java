package com.itk.kdp.service;

import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(MyTasksService.NAME)
public class MyTasksServiceBean implements MyTasksService {

    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private DataManager dataManager;

    @Override
    public long countTasksForCurrentUser() {
        LoadContext loadContext = LoadContext.create(ProcTask.class)
                .setQuery(
                        LoadContext
                        .createQuery("select pt from bpm$ProcTask pt left join pt.procActor pa left join pa.user pau " +
                                "where ((pau.id = :userId or " +
                                "   (pa is null and exists(" +
                                "       select pt2 from bpm$ProcTask pt2 join pt2.candidateUsers cu " +
                                "       where pt2.id = pt.id and cu.id = :userId))) " +
                                "       and pt.endDate is null)")
                        .setParameter("userId", getCurrentUser().getId()));

        return dataManager.getCount(loadContext);
    }

    private User getCurrentUser() {
        return userSessionSource.getUserSession().getCurrentOrSubstitutedUser();
    }
}