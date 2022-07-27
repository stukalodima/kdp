create table KDP_BUSINESS_TRIP (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER bigint,
    ON_DATE timestamp,
    EMPLOYEES_ID varchar(36),
    ORGANIZATION_ID varchar(36),
    DEPARTMENT_ID varchar(36),
    POSITION_ID varchar(36),
    PHONE_NUMBER varchar(255),
    START_DATE timestamp,
    END_DATE timestamp,
    DETAILS varchar(255),
    PURPOSE_ID varchar(36),
    TRANSPORT_ID varchar(36),
    HOTEL boolean,
    VISA boolean,
    ANALYTICS varchar(255),
    DESTINATION varchar(255),
    COMPANY_NAME varchar(255),
    PAY_CENTER varchar(50),
    IS_BUDGET varchar(255),
    BUDGET varchar(255),
    COMMENT varchar(255),
    AUTHOR_ID varchar(36),
    STATUS varchar(255),
    PLAIN boolean,
    TRAIN boolean,
    BUS boolean,
    AUTO_COMPANY boolean,
    AUTO_SELF boolean,
    PROC_INSTANCE_ID varchar(36),
    --
    primary key (ID)
);