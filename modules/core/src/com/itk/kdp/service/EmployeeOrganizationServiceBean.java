package com.itk.kdp.service;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.entity.Departments;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.VacationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service(EmployeeOrganizationService.NAME)
public class EmployeeOrganizationServiceBean implements EmployeeOrganizationService {

    public List<Employees> getEmployeeOrganization() {
        List<Employees> employees = new ArrayList<>();
//        UserSessionSource userSessionSource = AppBeans.get(UserSessionSource.class);
        DataManager dataManager = AppBeans.get(DataManager.class);

        Employees employee = null;
///        User user = userSessionSource.getUserSession().getUser();
        User user = getUser();
        if (!Objects.isNull(user.getEmail())) {
            String email = user.getEmail();

            employees = dataManager.load(Employees.class)
                    .query("select e from kdp_Employees e where e.workEmail = :workEmail")
                    .parameter("workEmail", email)
                    .view("employees-view")
                    .list();
        }
        return employees;
    }

    public User getUser(){
        UserSessionSource userSessionSource = AppBeans.get(UserSessionSource.class);
        return userSessionSource.getUserSession().getUser();
    }

}