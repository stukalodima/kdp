package com.itk.kdp.service;

import com.haulmont.cuba.core.global.DataManager;
import com.itk.kdp.entity.Departments;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service(GetDepartmensService.NAME)
public class GetDepartmensServiceBean implements GetDepartmensService {

    @Inject
    private DataManager dataManager;

    public List<Departments> getDepartmentsFilter (Departments editedEntity){

        List<Departments> departments = new ArrayList<>();
        getListDepartment(departments, editedEntity);

    return departments;
    }
    private void getListDepartment(List<Departments> departmentsList, Departments editedEntity) {
        List<Departments> list = dataManager.load(Departments.class)
                .query("select e from kdp_Departments e where e.parentId = :parentId")
                .parameter("parentId", editedEntity)
                .view("departments-parent-id")
                .list();

        list.forEach(e -> getListDepartment(departmentsList, e));

        departmentsList.add(editedEntity);
    }
}