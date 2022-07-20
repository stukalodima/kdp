-- begin KDP_POSITION
alter table KDP_POSITION add constraint FK_KDP_POSITION_ON_ORGANIZATIONS_ID foreign key (ORGANIZATIONS_ID_ID) references KDP_ORGANIZATIONS(ID);
create index IDX_KDP_POSITION_ON_ORGANIZATIONS_ID on KDP_POSITION (ORGANIZATIONS_ID_ID);
-- end KDP_POSITION
-- begin KDP_VACATION_REQUEST
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_EMPLOYEES foreign key (EMPLOYEES_ID) references KDP_EMPLOYEES(ID)^
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_COMPANY foreign key (COMPANY_ID) references KDP_ORGANIZATIONS(ID)^
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_DEPARTMENTS foreign key (DEPARTMENTS_ID) references KDP_DEPARTMENTS(ID)^
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_POSITION foreign key (POSITION_ID) references KDP_POSITION(ID)^
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_COORDINATOR foreign key (COORDINATOR_ID) references KDP_EMPLOYEES(ID)^
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_VACATION_TYPE foreign key (VACATION_TYPE_ID) references KDP_VACATION_TYPE(ID)^
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_DOCUMENT foreign key (DOCUMENT_ID) references SYS_FILE(ID)^
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_INITIATOR foreign key (INITIATOR_ID) references SEC_USER(ID)^
create index IDX_KDP_VACATION_REQUEST_ON_EMPLOYEES on KDP_VACATION_REQUEST (EMPLOYEES_ID)^
create index IDX_KDP_VACATION_REQUEST_ON_COMPANY on KDP_VACATION_REQUEST (COMPANY_ID)^
create index IDX_KDP_VACATION_REQUEST_ON_DEPARTMENTS on KDP_VACATION_REQUEST (DEPARTMENTS_ID)^
create index IDX_KDP_VACATION_REQUEST_ON_POSITION on KDP_VACATION_REQUEST (POSITION_ID)^
create index IDX_KDP_VACATION_REQUEST_ON_COORDINATOR on KDP_VACATION_REQUEST (COORDINATOR_ID)^
create index IDX_KDP_VACATION_REQUEST_ON_VACATION_TYPE on KDP_VACATION_REQUEST (VACATION_TYPE_ID)^
create index IDX_KDP_VACATION_REQUEST_ON_DOCUMENT on KDP_VACATION_REQUEST (DOCUMENT_ID)^
create index IDX_KDP_VACATION_REQUEST_ON_INITIATOR on KDP_VACATION_REQUEST (INITIATOR_ID)^
-- end KDP_VACATION_REQUEST
-- begin KDP_EMPLOYEES
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_COMPANY foreign key (COMPANY_ID) references KDP_ORGANIZATIONS(ID)^
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_DEPARTMENT foreign key (DEPARTMENT_ID) references KDP_DEPARTMENTS(ID)^
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_POSITION foreign key (POSITION_ID) references KDP_POSITION(ID)^
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_MANAGER foreign key (MANAGER_ID) references KDP_EMPLOYEES(ID)^
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_PHOTO foreign key (PHOTO_ID) references SYS_FILE(ID)^
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_USER foreign key (USER_ID) references SEC_USER(ID)^
create index IDX_KDP_EMPLOYEES_ON_COMPANY on KDP_EMPLOYEES (COMPANY_ID)^
create index IDX_KDP_EMPLOYEES_ON_DEPARTMENT on KDP_EMPLOYEES (DEPARTMENT_ID)^
create index IDX_KDP_EMPLOYEES_ON_POSITION on KDP_EMPLOYEES (POSITION_ID)^
create index IDX_KDP_EMPLOYEES_ON_MANAGER on KDP_EMPLOYEES (MANAGER_ID)^
create index IDX_KDP_EMPLOYEES_ON_PHOTO on KDP_EMPLOYEES (PHOTO_ID)^
create index IDX_KDP_EMPLOYEES_ON_USER on KDP_EMPLOYEES (USER_ID)^
-- end KDP_EMPLOYEES
-- begin KDP_DEPARTMENTS
alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_APPROVAL_MANAGER foreign key (APPROVAL_MANAGER_ID) references KDP_EMPLOYEES(ID);
alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_ORGANIZATION foreign key (ORGANIZATION_ID) references KDP_ORGANIZATIONS(ID);
alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_P_ID foreign key (P_ID_ID) references KDP_DEPARTMENTS(ID);
alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_MANAGER_ID foreign key (MANAGER_ID_ID) references KDP_EMPLOYEES(ID);
create unique index IDX_KDP_DEPARTMENTS_UK_CODE on KDP_DEPARTMENTS (CODE) where DELETE_TS is null ;
create index IDX_KDP_DEPARTMENTS_ON_APPROVAL_MANAGER on KDP_DEPARTMENTS (APPROVAL_MANAGER_ID);
create index IDX_KDP_DEPARTMENTS_ON_ORGANIZATION on KDP_DEPARTMENTS (ORGANIZATION_ID);
create index IDX_KDP_DEPARTMENTS_ON_P_ID on KDP_DEPARTMENTS (P_ID_ID);
create index IDX_KDP_DEPARTMENTS_ON_MANAGER_ID on KDP_DEPARTMENTS (MANAGER_ID_ID);
-- end KDP_DEPARTMENTS
-- begin KDP_COUNTRIES
create unique index IDX_KDP_COUNTRIES_UK_CODE on KDP_COUNTRIES (CODE) where DELETE_TS is null ^
-- end KDP_COUNTRIES
-- begin KDP_ORGANIZATIONS
alter table KDP_ORGANIZATIONS add constraint FK_KDP_ORGANIZATIONS_ON_COUNTRY_REGISTRATION foreign key (COUNTRY_REGISTRATION_ID) references KDP_COUNTRIES(ID)^
create index IDX_KDP_ORGANIZATIONS_ON_COUNTRY_REGISTRATION on KDP_ORGANIZATIONS (COUNTRY_REGISTRATION_ID)^
-- end KDP_ORGANIZATIONS
-- begin KDP_BUSINESS_TRIP
alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_EMPLOYEES foreign key (EMPLOYEES_ID) references KDP_EMPLOYEES(ID)^
alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_ORGANIZATION foreign key (ORGANIZATION_ID) references KDP_ORGANIZATIONS(ID)^
alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_DEPARTMENT foreign key (DEPARTMENT_ID) references KDP_DEPARTMENTS(ID)^
alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_POSITION foreign key (POSITION_ID) references KDP_POSITION(ID)^
alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_PURPOSE foreign key (PURPOSE_ID) references KDP_PURPOSE(ID)^
alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_TRANSPORT foreign key (TRANSPORT_ID) references KDP_TRANSPORT(ID)^
alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_AUTHOR foreign key (AUTHOR_ID) references KDP_EMPLOYEES(ID)^
alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_PROC_INSTANCE foreign key (PROC_INSTANCE_ID) references BPM_PROC_INSTANCE(ID)^
create index IDX_KDP_BUSINESS_TRIP_ON_EMPLOYEES on KDP_BUSINESS_TRIP (EMPLOYEES_ID)^
create index IDX_KDP_BUSINESS_TRIP_ON_ORGANIZATION on KDP_BUSINESS_TRIP (ORGANIZATION_ID)^
create index IDX_KDP_BUSINESS_TRIP_ON_DEPARTMENT on KDP_BUSINESS_TRIP (DEPARTMENT_ID)^
create index IDX_KDP_BUSINESS_TRIP_ON_POSITION on KDP_BUSINESS_TRIP (POSITION_ID)^
create index IDX_KDP_BUSINESS_TRIP_ON_PURPOSE on KDP_BUSINESS_TRIP (PURPOSE_ID)^
create index IDX_KDP_BUSINESS_TRIP_ON_TRANSPORT on KDP_BUSINESS_TRIP (TRANSPORT_ID)^
create index IDX_KDP_BUSINESS_TRIP_ON_AUTHOR on KDP_BUSINESS_TRIP (AUTHOR_ID)^
create index IDX_KDP_BUSINESS_TRIP_ON_PROC_INSTANCE on KDP_BUSINESS_TRIP (PROC_INSTANCE_ID)^
-- end KDP_BUSINESS_TRIP
-- begin KDP_ADDRESSING
alter table KDP_ADDRESSING add constraint FK_KDP_ADDRESSING_ON_PROC_DEFINITION foreign key (PROC_DEFINITION_ID) references BPM_PROC_DEFINITION(ID)^
alter table KDP_ADDRESSING add constraint FK_KDP_ADDRESSING_ON_COMPANY foreign key (COMPANY_ID) references KDP_ORGANIZATIONS(ID)^
create index IDX_KDP_ADDRESSING_ON_PROC_DEFINITION on KDP_ADDRESSING (PROC_DEFINITION_ID)^
create index IDX_KDP_ADDRESSING_ON_COMPANY on KDP_ADDRESSING (COMPANY_ID)^
-- end KDP_ADDRESSING
-- begin KDP_ADDRESSING_DETAIL
alter table KDP_ADDRESSING_DETAIL add constraint FK_KDP_ADDRESSING_DETAIL_ON_PROC_ROLE foreign key (PROC_ROLE_ID) references BPM_PROC_ROLE(ID)^
alter table KDP_ADDRESSING_DETAIL add constraint FK_KDP_ADDRESSING_DETAIL_ON_USER foreign key (USER_ID) references SEC_USER(ID)^
alter table KDP_ADDRESSING_DETAIL add constraint FK_KDP_ADDRESSING_DETAIL_ON_ADDRESSING foreign key (ADDRESSING_ID) references KDP_ADDRESSING(ID)^
create index IDX_KDP_ADDRESSING_DETAIL_ON_PROC_ROLE on KDP_ADDRESSING_DETAIL (PROC_ROLE_ID)^
create index IDX_KDP_ADDRESSING_DETAIL_ON_USER on KDP_ADDRESSING_DETAIL (USER_ID)^
create index IDX_KDP_ADDRESSING_DETAIL_ON_ADDRESSING on KDP_ADDRESSING_DETAIL (ADDRESSING_ID)^
-- end KDP_ADDRESSING_DETAIL
-- begin KDP_BUSINESS_TRIP_FILES
alter table KDP_BUSINESS_TRIP_FILES add constraint FK_KDP_BUSINESS_TRIP_FILES_ON_DOCUMENT foreign key (DOCUMENT_ID) references SYS_FILE(ID)^
alter table KDP_BUSINESS_TRIP_FILES add constraint FK_KDP_BUSINESS_TRIP_FILES_ON_BUSINESS_TRIP foreign key (BUSINESS_TRIP_ID) references KDP_BUSINESS_TRIP(ID)^
create index IDX_KDP_BUSINESS_TRIP_FILES_ON_DOCUMENT on KDP_BUSINESS_TRIP_FILES (DOCUMENT_ID)^
create index IDX_KDP_BUSINESS_TRIP_FILES_ON_BUSINESS_TRIP on KDP_BUSINESS_TRIP_FILES (BUSINESS_TRIP_ID)^
-- end KDP_BUSINESS_TRIP_FILES
