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
)^
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
    NAME varchar(255),
    --
    primary key (ID)
);
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
-- begin KDP_DEPARTMENT
create table KDP_DEPARTMENT (
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
    NAME varchar(255) not null,
    ORGANIZATION_ID varchar(36) not null,
    --
    primary key (ID)
)^
-- end KDP_DEPARTMENT
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
