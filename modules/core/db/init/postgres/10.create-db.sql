-- begin KDP_POSITION
create table KDP_POSITION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    POSITION_EN varchar(255),
    POSITION_UA varchar(255),
    POSITION_RU varchar(255),
    POSITION_1C_ID varchar(255),
    ORGANIZATIONS_ID_ID uuid not null,
    --
    primary key (ID)
)^
-- end KDP_POSITION
-- begin KDP_VACATION_REQUEST
create table KDP_VACATION_REQUEST (
    ID uuid,
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
    EMPLOYEES_ID uuid not null,
    COMPANY_ID uuid not null,
    DEPARTMENTS_ID uuid,
    POSITION_ID uuid,
    COORDINATOR_ID uuid not null,
    VACATION_TYPE_ID uuid,
    REMAINING_VACATION_DAYS integer,
    DATE_FROM date,
    DATE_BY date,
    VACATION_DAYS integer,
    NOTE varchar(255),
    CONSENT_TO_BILLING boolean,
    INITIATOR_ID uuid,
    UPLOAD_TO_1C boolean,
    STATUS varchar(255),
    COMMENT varchar(255),
    PROC_INSTANCE_ID uuid,
    PHONE_NUMBER varchar(255),
    --
    primary key (ID)
)^
-- end KDP_VACATION_REQUEST
-- begin KDP_ORGANIZATIONS
create table KDP_ORGANIZATIONS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    FULL_NAME varchar(255),
    ORGANIZATIONS_EN varchar(255),
    ORGANIZATIONS_UA varchar(255),
    ORGANIZATIONS_RU varchar(255),
    CODE_OCPO varchar(12),
    DATE_REGISTRATION date,
    COUNTRY_REGISTRATION_ID uuid,
    ORGANIZATIONS1C_ID varchar(255),
    ENTITY boolean,
    ACTIVE boolean,
    --
    primary key (ID)
)^
-- end KDP_ORGANIZATIONS
-- begin KDP_EMPLOYEES
create table KDP_EMPLOYEES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SURNAME_UA varchar(255),
    NAME_UA varchar(255),
    MIDDLE_NAME_UA varchar(255),
    SURNAME_RU varchar(255),
    NAME_RU varchar(255),
    MIDDLE_NAME_RU varchar(255),
    SURNAME_EN varchar(255),
    NAME_EN varchar(255),
    MIDDLE_NAME_EN varchar(255),
    WORK_EMAIL varchar(255),
    OTHER_EMAIL varchar(255),
    WORK_PHONE varchar(255),
    MOBILE_PHONE varchar(255),
    COMPANY_ID uuid,
    DEPARTMENT_ID uuid,
    POSITION_ID uuid,
    MANAGER_ID uuid,
    BIRTHDAY date,
    EMPLOYMENT_DATE date,
    LOGIN_NAME varchar(255),
    FORM_EMPLOYMENT boolean,
    EMPLOYEE_1C_ID varchar(255),
    PHOTO_ID uuid,
    USER_ID uuid,
    --
    primary key (ID)
)^
-- end KDP_EMPLOYEES
-- begin KDP_RECONCILIATION_STATUSES
create table KDP_RECONCILIATION_STATUSES (
    ID uuid,
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
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE bigint,
    APPROVAL_MANAGER_ID uuid,
    DEPARTMENT_EN varchar(255),
    DEPARTMENT_UA varchar(255),
    DEPARTMENT_RU varchar(255),
    DEPARTMENT_1C_ID varchar(255),
    ORGANIZATION_ID uuid,
    P_ID_ID uuid,
    MANAGER_ID_ID uuid,
    --
    primary key (ID)
)^
-- end KDP_DEPARTMENTS
-- begin KDP_VACATION_TYPE
create table KDP_VACATION_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME_UA varchar(255),
    NAME_EN varchar(255),
    NAME_RU varchar(255),
    VACATION_TYPE_1C varchar(255),
    --
    primary key (ID)
)^
-- end KDP_VACATION_TYPE
-- begin KDP_COUNTRIES
create table KDP_COUNTRIES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE integer,
    NAME_UA varchar(255),
    NAME_EN varchar(255),
    NAME_Ru varchar(255),
    --
    primary key (ID)
)^
-- end KDP_COUNTRIES
-- begin KDP_BUSINESS_TRIP
create table KDP_BUSINESS_TRIP (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER bigint,
    ON_DATE timestamp,
    EMPLOYEES_ID uuid,
    ORGANIZATION_ID uuid,
    DEPARTMENT_ID uuid,
    POSITION_ID uuid,
    PHONE_NUMBER varchar(255),
    START_DATE timestamp,
    END_DATE timestamp,
    DETAILS varchar(255),
    PURPOSE_ID uuid,
    TRANSPORT_ID uuid,
    HOTEL boolean,
    VISA boolean,
    ANALYTICS varchar(255),
    DESTINATION varchar(255),
    START_PLACE varchar(255),
    COMPANY_NAME varchar(255),
    PAY_CENTER varchar(50),
    IS_BUDGET varchar(255),
    BUDGET varchar(255),
    COMMENT varchar(255),
    AUTHOR_ID uuid,
    STATUS varchar(255),
    PLAIN boolean,
    TRAIN boolean,
    BUS boolean,
    AUTO_COMPANY boolean,
    AUTO_SELF boolean,
    PROC_INSTANCE_ID uuid,
    --
    primary key (ID)
)^
-- end KDP_BUSINESS_TRIP
-- begin KDP_PURPOSE
create table KDP_PURPOSE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    NAME_RU varchar(255),
    NAME_EN varchar(255),
    ACTIVE boolean,
    --
    primary key (ID)
)^
-- end KDP_PURPOSE
-- begin KDP_TRANSPORT
create table KDP_TRANSPORT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME_UA varchar(255),
    NAME_EN varchar(255),
    NAME_RU varchar(255),
    ACTIVE boolean,
    --
    primary key (ID)
)^
-- end KDP_TRANSPORT
-- begin KDP_ADDRESSING
create table KDP_ADDRESSING (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PROC_ENTITY varchar(50),
    PROC_DEFINITION_ID uuid not null,
    USE_COMPANY boolean,
    COMPANY_ID uuid,
    --
    primary key (ID)
)^
-- end KDP_ADDRESSING
-- begin KDP_ADDRESSING_DETAIL
create table KDP_ADDRESSING_DETAIL (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PROC_ROLE_ID uuid not null,
    USER_ID uuid,
    IS_INITIAL boolean,
    IS_NANAGER boolean,
    AUTO boolean,
    ADDRESSING_ID uuid not null,
    --
    primary key (ID)
)^
-- end KDP_ADDRESSING_DETAIL
-- begin KDP_BUSINESS_TRIP_FILES
create table KDP_BUSINESS_TRIP_FILES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOCUMENT_ID uuid,
    BUSINESS_TRIP_ID uuid,
    COMMENT varchar(255),
    TASK varchar(255),
    AUTHOR_ID uuid,
    --
    primary key (ID)
)^
-- end KDP_BUSINESS_TRIP_FILES
-- begin KDP_ACCESS_REQUEST
create table KDP_ACCESS_REQUEST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    EMPLOYEE_ID uuid,
    COMPANY_ID uuid,
    MANAGER_ID uuid,
    ACCESS_TYPE varchar(50),
    TECH_INFO varchar(255),
    BUSINESS_INFO varchar(255),
    START_DATE timestamp,
    END_DATE timestamp,
    PROC_INSTANCE uuid,
    --
    primary key (ID)
)^
-- end KDP_ACCESS_REQUEST
-- begin KDP_VACATION_FILES
create table KDP_VACATION_FILES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOCUMENT_ID uuid,
    VACATION_ID uuid,
    COMMENT varchar(255),
    TASK varchar(255),
    AUTHOR_ID uuid,
    --
    primary key (ID)
)^
-- end KDP_VACATION_FILES
-- begin KDP_VACATION_BALANCE
create table KDP_VACATION_BALANCE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    EMPLOYEE_ID uuid,
    VACATION_TYPE_ID uuid,
    days integer,
    --
    primary key (ID)
)^
-- end KDP_VACATION_BALANCE
