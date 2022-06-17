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
);