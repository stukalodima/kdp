create table KDP_USERS_FPMNT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME_UA varchar(255),
    NAME_En varchar(255),
    NAME_RU varchar(255),
    USER_TYPE varchar(255),
    --
    primary key (ID)
);