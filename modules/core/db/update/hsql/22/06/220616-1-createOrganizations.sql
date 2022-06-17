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
);