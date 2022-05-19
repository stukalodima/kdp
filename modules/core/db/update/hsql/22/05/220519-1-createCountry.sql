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
);