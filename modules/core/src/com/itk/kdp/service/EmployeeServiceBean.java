package com.itk.kdp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.app.UserManagementService;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.User;
import com.itk.kdp.config.RestApiConfig;
import com.itk.kdp.entity.Employees;
import com.itk.kdp.entity.Organizations;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(EmployeeService.NAME)
public class EmployeeServiceBean implements EmployeeService {

    @SuppressWarnings("all")
    @Inject
    private RestClientService restClientService;
    @SuppressWarnings("all")
    @Inject
    private DataManager dataManager;
    @SuppressWarnings("all")
    @Inject
    private CompanyService companyService;
    @SuppressWarnings("all")
    @Inject
    private DepartmentService departmentService;
    @SuppressWarnings("all")
    @Inject
    private PositionService positionService;
    @SuppressWarnings("all")
    @Inject
    private Metadata metadata;
    @SuppressWarnings("all")
    @Inject
    private FileLoader fileLoader;
    @SuppressWarnings("all")
    @Inject
    private UserManagementService userManagementService;
    @SuppressWarnings("all")
    @Inject
    private MessageTools messageTools;
    @SuppressWarnings("all")
    @Inject
    private RestApiConfig restApiConfig;
    @SuppressWarnings("all")
    @Inject
    private UserSessionSource userSessionSource;
    @SuppressWarnings("all")
    @Inject
    private TimeSource timeSource;

    @Override
    public void getEmployeeListFromExternal() throws IOException {
        String connectString = restApiConfig.getRestApiEmployeeService();
        List<Organizations> organizationsList = companyService.getCompanyListByActive();
        callRestApi(connectString, organizationsList);
        connectString = restApiConfig.getRestApiExtEmployeeService();
        callRestApi(connectString, companyService.getCompanyListByActive(), true);
    }

    private void callRestApi(String connectString, List<Organizations> organizationsList, boolean extStaff) throws IOException {
        for (Organizations organizations : organizationsList) {
            String jsonString = restClientService.callGetMethod(connectString + "?companyId=" + organizations.getOrganizations1cId() + "&all=1", true);
            if (!jsonString.isEmpty()) {
                try {
                    parseJsonString(jsonString, extStaff);
                } catch (ParseException e) {
                    throw new IOException(e);
                }
            }
        }
    }

    private void callRestApi(String connectString, List<Organizations> organizationsList) throws IOException {
        callRestApi(connectString, organizationsList, false);
    }

    @Override
    public Employees getEmployeeByCode(String code) {
        List<Employees> employeesList = dataManager.load(Employees.class)
                .query("select e from kdp_Employees e where e.employee1cId = :code")
                .parameter("code", code)
                .view("employees-for-create-api")
                .list();
        Employees employees = null;
        if (employeesList.size() > 0) {
            employees = employeesList.get(0);
        }
        return employees;
    }

    @Override
    public Employees getEmployeeById(String id) {
        UUID uuid = UUID.fromString(id);
        List<Employees> employeesList = dataManager.load(Employees.class)
                .query("select e from kdp_Employees e where e.id = :id")
                .parameter("id", uuid)
                .view("employees-for-create-api")
                .list();
        Employees employees = null;
        if (employeesList.size() > 0) {
            employees = employeesList.get(0);
        }
        return employees;
    }

    @Override
    public Employees getEmployeeByUser(User user) {
        List<Employees> employeesList = dataManager.load(Employees.class)
                .query("select e from kdp_Employees e where e.user = :user and e.formEmployment = :form")
                .parameter("user", user)
                .parameter("form", true)
                .view("employees-for-create-api")
                .list();

        if (employeesList.isEmpty()) {
            return null;
        }
        return employeesList.get(0);
    }

    private void parseJsonString(String jsonString, boolean extStaff) throws ParseException {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        HashMap<String, String> employeeMap = new HashMap<>();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (!extStaff) {
                employeeMap.put("nameUa", jsonObject.getAsJsonPrimitive("nameUa").getAsString());
                employeeMap.put("surnameUa", jsonObject.getAsJsonPrimitive("surnameUa").getAsString());
                employeeMap.put("middleNameUa", jsonObject.getAsJsonPrimitive("middleNameUa").getAsString());
                employeeMap.put("nameRu", jsonObject.getAsJsonPrimitive("nameRu").getAsString());
                employeeMap.put("surnameRu", jsonObject.getAsJsonPrimitive("surnameRu").getAsString());
                employeeMap.put("middleNameRu", jsonObject.getAsJsonPrimitive("middleNameRu").getAsString());
                employeeMap.put("loginName", jsonObject.getAsJsonPrimitive("loginName").getAsString());
                employeeMap.put("workEmail", jsonObject.getAsJsonPrimitive("workEmail").getAsString());
                employeeMap.put("otherEmail", jsonObject.getAsJsonPrimitive("otherEmail").getAsString());
                employeeMap.put("workPhone", jsonObject.getAsJsonPrimitive("workPhone").getAsString());
                employeeMap.put("mobilePhone", jsonObject.getAsJsonPrimitive("mobilePhone").getAsString());
                employeeMap.put("departmentId", jsonObject.getAsJsonPrimitive("departmentId").getAsString());
                employeeMap.put("positionId", jsonObject.getAsJsonPrimitive("positionId").getAsString());
                employeeMap.put("managerId", jsonObject.getAsJsonPrimitive("managerId").getAsString());
                employeeMap.put("approvalManager", jsonObject.getAsJsonPrimitive("approvalManager").getAsString());
                employeeMap.put("vacationManager", jsonObject.getAsJsonPrimitive("vacationManager").getAsString());
                employeeMap.put("GUID", jsonObject.getAsJsonPrimitive("GUID").getAsString());
                employeeMap.put("photo", jsonObject.getAsJsonPrimitive("photo").getAsString());
                employeeMap.put("formEmployment", jsonObject.getAsJsonPrimitive("formEmployment").getAsString());
                employeeMap.put("birthday", jsonObject.getAsJsonPrimitive("birthday").getAsString());
                employeeMap.put("employmentDate", jsonObject.getAsJsonPrimitive("employmentDate").getAsString());
            }

            employeeMap.put("companyId", jsonObject.getAsJsonPrimitive("companyId").getAsString());
            employeeMap.put("employee1cId", jsonObject.getAsJsonPrimitive("employeeId").getAsString());

            if (extStaff) {
                fireEmployee(employeeMap);
            } else {
                fillEmployeeEntity(employeeMap);
            }
        }
    }

    private void fireEmployee(HashMap<String, String> employeeMap) {
        Employees employees = getEmployeeByCode(employeeMap.get("employee1cId"));

        if (!Objects.isNull(employees)) {
            employees.setDeletedBy(userSessionSource.getUserSession().getUser().getLogin());
            employees.setDeleteTs(timeSource.currentTimestamp());
            User user = employees.getUser();
            user.setActive(false);
            user.setDeletedBy(userSessionSource.getUserSession().getUser().getLogin());
            user.setDeleteTs(timeSource.currentTimestamp());
            dataManager.commit(user);
            dataManager.commit(employees);
        }
    }

    private void fillEmployeeEntity(HashMap<String, String> employeeMap) throws ParseException {
        Employees employees = getEmployeeByCode(employeeMap.get("employee1cId"));

        if (Objects.isNull(employees)) {
            employees = dataManager.create(Employees.class);
        }

        String format = "MM/dd/yyyy hh:mm:ss aaa";
        Date birthday = null;
        Date employmentDate = null;
        if (!StringUtil.isBlank(employeeMap.get("birthday"))) {
            birthday = new SimpleDateFormat(format).parse(employeeMap.get("birthday"));
        }
        if (!StringUtil.isBlank(employeeMap.get("employmentDate"))) {
            employmentDate = new SimpleDateFormat(format).parse(employeeMap.get("employmentDate"));
        }
//        employees.setId(UUID.fromString(employeeMap.get("GUID")));
        employees.setEmployee1cId(employeeMap.get("employee1cId"));
//        employees.setEmployee1cId(employeeMap.get("companyId") + "$" + employeeMap.get("department1cId"));
        employees.setNameUa(employeeMap.get("nameUa"));
        employees.setSurnameUa(employeeMap.get("surnameUa"));
        employees.setMiddleNameUa(employeeMap.get("middleNameUa"));
        employees.setNameRu(employeeMap.get("nameRu"));
        employees.setSurnameRu(employeeMap.get("surnameRu"));
        employees.setMiddleNameRu(employeeMap.get("middleNameRu"));
        employees.setLoginName(employeeMap.get("loginName"));
        employees.setWorkEmail(employeeMap.get("workEmail"));
        employees.setOtherEmail(employeeMap.get("otherEmail"));
        employees.setWorkPhone(employeeMap.get("workPhone"));
        employees.setMobilePhone(employeeMap.get("mobilePhone"));
        employees.setCompany(companyService.getCompanyByCode(employeeMap.get("companyId")));
        employees.setDepartment(departmentService.getDepartmentByCode(employeeMap.get("companyId") + "$" + employeeMap.get("departmentId")));
        employees.setPosition(positionService.getPositionByCode(employeeMap.get("companyId") + "$" + employeeMap.get("positionId")));
        employees.setManager(getEmployeeByCode(employeeMap.get("managerId")));
        employees.setApprovalManager(getEmployeeByCode(employeeMap.get("approvalManager")));
        employees.setVacationManager(getEmployeeByCode(employeeMap.get("vacationManager")));
        employees.setFormEmployment(employeeMap.get("formEmployment").equals("1"));
        employees.setBirthday(birthday);
        employees.setEmploymentDate(employmentDate);
        //managerId
        CommitContext commitContext = new CommitContext();

        if (!StringUtil.isBlank(employeeMap.get("photo"))) {
            FileDescriptor fileDescriptor;
            if (employees.getPhoto() != null) {
                fileDescriptor = employees.getPhoto();
            } else {
                fileDescriptor = metadata.create(FileDescriptor.class);
            }
            byte[] bytes = Base64.decodeBase64(employeeMap.get("photo"));
            fileDescriptor.setName("ava.png");
            fileDescriptor.setExtension("png");
            fileDescriptor.setSize((long) bytes.length);
            fileDescriptor.setCreateDate(timeSource.currentTimestamp());
            try {
                if (fileLoader.fileExists(fileDescriptor)) {
                    fileLoader.removeFile(fileDescriptor);
                }
                fileLoader.saveStream(fileDescriptor, () -> new ByteArrayInputStream(bytes));
            } catch (FileStorageException e) {
                throw new RuntimeException("Error save fd to file storage", e);
            }
            employees.setPhoto(fileDescriptor);

            commitContext.addInstanceToCommit(fileDescriptor);
        }
        if (employees.getUser() == null) {
            if (!Objects.isNull(employees.getLoginName()) && !StringUtil.isBlank(employees.getLoginName())) {
                String login = employees.getLoginName();
                int index = login.indexOf('@');
                login = login.substring(0, index);

                employees.setLoginName(login);

                List<User> userList = dataManager.load(User.class)
                        .query("select e from sec$User e where LOWER(e.login) = LOWER(:login)")
                        .parameter("login", login)
                        .view("user.edit")
                        .list();
                User user;
                if (userList.isEmpty()) {
                    user = metadata.create(User.class);
                } else {
                    user = userList.get(0);
                }

                List<Group> groupList = dataManager.load(Group.class)
                        .query("select e from sec$Group e where e.name = :group")
                        .parameter("group", "Company")
                        .list();

                user.setLogin(employees.getLoginName());
                user.setName(employees.getCaption(false));
                user.setFirstName(employees.getNameUa());
                user.setMiddleName(employees.getMiddleNameUa());
                user.setLastName(employees.getSurnameUa());
                user.setEmail(employees.getWorkEmail());
                user.setPosition(employees.getPosition().getCaption());
                user.setTimeZoneAuto(true);
                user.setLanguage(messageTools.getDefaultLocale().getLanguage());
                if (!groupList.isEmpty()) {
                    user.setGroup(groupList.get(0));
                }
                user.setActive(true);

                employees.setUser(user);
                commitContext.addInstanceToCommit(user);
            }
        }
        commitContext.addInstanceToCommit(employees);

        dataManager.commit(commitContext);

        if (!Objects.isNull(employees.getUser())) {
            List<UUID> users = Collections.singletonList(employees.getUser().getId());
            userManagementService.moveUsersToGroup(users, "Company");
        }
    }
}