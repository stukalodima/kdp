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
);