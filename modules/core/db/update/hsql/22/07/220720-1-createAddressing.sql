create table KDP_ADDRESSING (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PROC_DEFINITION_ID varchar(36) not null,
    USE_COMPANY boolean,
    COMPANY_ID varchar(36),
    --
    primary key (ID)
);