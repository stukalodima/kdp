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
);