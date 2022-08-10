create table KDP_OPERATOR_ACCESS_REQUEST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    OPERATOR_ID uuid,
    EMPLOYEE_ID uuid,
    USER_ID uuid,
    START_DATE timestamp,
    END_DATE timestamp,
    COMMENT text,
    --
    primary key (ID)
);