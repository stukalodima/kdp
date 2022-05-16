-- begin KDP_EMPLOYEE
create table KDP_EMPLOYEE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TAB_NUMBER varchar(255) not null,
    FIO varchar(255) not null,
    FIRST_NAME varchar(255) not null,
    SECOND_NAME varchar(255) not null,
    LAST_NAME varchar(255),
    BIRTH_DATE date,
    COMPANY_ID varchar(36) not null,
    STREET_ID varchar(36),
    --
    primary key (ID)
)^
-- end KDP_EMPLOYEE
-- begin KDP_COMPANY
create table KDP_COMPANY (
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
)^
-- end KDP_COMPANY
-- begin KDP_STREETS
create table KDP_STREETS (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(10) not null,
    NAME varchar(50) not null,
    NOTE varchar(255),
    --
    primary key (ID)
)^
-- end KDP_STREETS
