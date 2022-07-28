create table KDP_VACATION_FILES (
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
    VACATION_ID uuid,
    COMMENT varchar(255),
    TASK varchar(255),
    AUTHOR_ID uuid,
    --
    primary key (ID)
);