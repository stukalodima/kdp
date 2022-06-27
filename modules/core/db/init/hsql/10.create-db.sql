-- begin KDP_ORGANIZATIONS
create table KDP_ORGANIZATIONS (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(9) not null,
    PREFIX varchar(3) not null,
    SHORT_NAME varchar(255) not null,
    FULL_NAME varchar(255) not null,
    ORGANIZATIONS_EN varchar(255) not null,
    ORGANIZATIONS_UA varchar(255) not null,
    ORGANIZATIONS_RU varchar(255) not null,
    CODE_OCPO varchar(12) not null,
    DATE_REGISTRATION date,
    COUNTRY_REGISTRATION varchar(255),
    ORGANIZATIONS1C_ID varchar(36),
    ENTITY boolean,
    ACTIVE boolean,
    --
    primary key (ID)
);
-- end KDP_ORGANIZATIONS
-- begin KDP_POSITION
create table KDP_POSITION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    POSITION_EN varchar(255) not null,
    POSITION_UA varchar(255) not null,
    POSITION_RU varchar(255) not null,
    ORGANIZATIONS_ID_ID varchar(36) not null,
    --
    primary key (ID)
)^
-- end KDP_POSITION
-- end KDP_RECONCILIATION_STATUSES
-- begin KDP_VACATION_TYPE
create table KDP_VACATION_TYPE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255) not null,
    --
    primary key (ID)
);
-- end KDP_VACATION_TYPE

-- begin KDP_RECONCILIATION_STATUSES
create table KDP_RECONCILIATION_STATUSES (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
);
-- end KDP_RECONCILIATION_STATUSES
-- begin KDP_DEPARTMENTS
create table KDP_DEPARTMENTS (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE integer not null,
    APPROVAL_MANAGER_ID varchar(36),
    NAME varchar(255) not null,
    DEPARTMENT_EN varchar(255) not null,
    DEPARTMENT_UA varchar(255) not null,
    DEPARTMENT_RU varchar(255) not null,
    DEPARTMENT_ID varchar(36),
    ORGANIZATION_ID varchar(36) not null,
    P_ID_ID varchar(36),
    MANAGER_ID_ID varchar(36),
    --
    primary key (ID)
)^
-- end KDP_DEPARTMENTS
-- begin KDP_EMPLOYEES
create table KDP_EMPLOYEES (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SURNAME_UA varchar(250) not null,
    NAME_UA varchar(250) not null,
    MIDDLE_NAME_UA varchar(250) not null,
    SURNAME_RU varchar(250) not null,
    NAME_RU varchar(250) not null,
    MIDDLE_NAME_RU varchar(250) not null,
    SURNAME_EN varchar(250) not null,
    NAME_EN varchar(250) not null,
    MIDDLE_NAME_EN varchar(250),
    WORK_EMAIL varchar(100) not null,
    OTHER_EMAIL varchar(100),
    WORK_PHONE varchar(15),
    MOBILE_PHONE varchar(15),
    COMPANY_ID varchar(36) not null,
    DEPARTMENT_ID varchar(36) not null,
    POSITION_ID varchar(36) not null,
    MANAGER_ID varchar(36),
    BIRTHDAY date,
    EMPLOYMENT_DATE date,
    LOGIN_NAME varchar(250) not null,
    FORM_EMPLOYMENT boolean,
    EMPLOYEE_1C_ID varchar(36),
    PHOTO_ID varchar(36),
    --
    primary key (ID)
)^
-- end KDP_EMPLOYEES
-- begin KDP_VACATION_REQUEST
create table KDP_VACATION_REQUEST (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    APPLICATION_NUMBER integer not null,
    APPLICATION_DATE date,
    EMPLOYEES_ID varchar(36) not null,
    COMPANY_ID varchar(36) not null,
    DEPARTMENTS_ID varchar(36),
    POSITION_ID varchar(36),
    COORDINATOR_ID varchar(36) not null,
    VACATION_TYPE_ID varchar(36),
    REMAINING_VACATION_DAYS integer,
    DATE_FROM date,
    DATE_BY date,
    VACATION_DAYS integer,
    INFORMATION_FOR_CONSULTATIONS varchar(255),
    NOTE varchar(255),
    CONSENT_TO_BILLING boolean,
    document_ID varchar(36),
    INITIATOR_ID varchar(36),
    UPLOAD_TO_1C boolean,
    STATUS varchar(255),
    COMMENT varchar(255),
    --
    primary key (ID)
)^
-- end KDP_VACATION_REQUEST
