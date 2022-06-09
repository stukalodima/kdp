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
    SHORT_NAME varchar(255) not null,
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
)^
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
