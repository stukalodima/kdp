create table KDP_IDEA_ATTACHMENTS (
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
    AUTHOR_ID uuid,
    COMMENT_ text,
    IDEA_ID uuid not null,
    --
    primary key (ID)
);