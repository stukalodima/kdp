create table KDP_BUSINESS_TRIP_FILES (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOCUMENT_ID varchar(36),
    BUSINESS_TRIP_ID varchar(36),
    COMMENT varchar(255),
    --
    primary key (ID)
);