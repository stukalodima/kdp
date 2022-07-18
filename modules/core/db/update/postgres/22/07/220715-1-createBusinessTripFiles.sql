create table KDP_BUSINESS_TRIP_FILES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOCUMENT_ID uuid,
    BUSINESS_TRIP_ID uuid,
    --
    primary key (ID)
);