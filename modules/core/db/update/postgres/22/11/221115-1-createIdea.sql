create table KDP_IDEA (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    AUTHOR_ID uuid,
    MANAGER_ID uuid,
    NAME varchar(255),
    DESCRIPTION text,
    PROBLEM_SOLVE text,
    BENEFITS text,
    AUTOMATION boolean,
    RESOURCES text,
    EXECUTOR_ID uuid,
    --
    primary key (ID)
);