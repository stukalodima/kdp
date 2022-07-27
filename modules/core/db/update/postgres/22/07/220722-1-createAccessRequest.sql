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
);