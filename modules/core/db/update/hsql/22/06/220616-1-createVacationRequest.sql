create table KDP_VACATION_REQUEST (
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
    Employees_ID varchar(36) not null,
    COMPANY_ID varchar(36) not null,
    Departments_ID varchar(36) not null,
    Position_ID varchar(36) not null,
    MANAGER_ID varchar(36),
    VacationType_ID varchar(36),
    REMAINING_VACATION_DAYS integer,
    DATE_FROM date,
    DATE_BY date,
    VACATION_DAYS integer,
    INFORMATION_FOR_CONSULTATIONS varchar(255),
    Note varchar(255),
    Initiator_ID varchar(36),
    STATUS varchar(255),
    COMMENT varchar(255),
    --
    primary key (ID)
);