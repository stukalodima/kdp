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
-- begin KDP_COUNTRY
create table KDP_COUNTRY (
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
    CODE_NUMERIC integer not null,
    CODE_ALPHA3 varchar(3),
    CODE_ALPHA2 varchar(2),
    --
    primary key (ID)
)^
-- end KDP_COUNTRY
