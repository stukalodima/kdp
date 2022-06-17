-- begin KDP_DEPARTMENTS
alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_APPROVAL_MANAGER foreign key (APPROVAL_MANAGER_ID) references KDP_EMPLOYEES(ID);
alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_ORGANIZATION foreign key (ORGANIZATION_ID) references KDP_ORGANIZATIONS(ID);
alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_P_ID foreign key (P_ID_ID) references KDP_DEPARTMENTS(ID);
alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_MANAGER_ID foreign key (MANAGER_ID_ID) references KDP_EMPLOYEES(ID);
create unique index IDX_KDP_DEPARTMENTS_UNIQ_CODE on KDP_DEPARTMENTS (CODE);
create index IDX_KDP_DEPARTMENTS_ON_APPROVAL_MANAGER on KDP_DEPARTMENTS (APPROVAL_MANAGER_ID);
create index IDX_KDP_DEPARTMENTS_ON_ORGANIZATION on KDP_DEPARTMENTS (ORGANIZATION_ID);
create index IDX_KDP_DEPARTMENTS_ON_P_ID on KDP_DEPARTMENTS (P_ID_ID);
create index IDX_KDP_DEPARTMENTS_ON_MANAGER_ID on KDP_DEPARTMENTS (MANAGER_ID_ID);
-- end KDP_DEPARTMENTS
-- begin KDP_EMPLOYEES
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_COMPANY foreign key (COMPANY_ID) references KDP_ORGANIZATIONS(ID);
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_DEPARTMENT foreign key (DEPARTMENT_ID) references KDP_DEPARTMENTS(ID);
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_POSITION foreign key (POSITION_ID) references KDP_POSITION(ID);
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_MANAGER foreign key (MANAGER_ID) references KDP_EMPLOYEES(ID);
alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_PHOTO foreign key (PHOTO_ID) references SYS_FILE(ID);
create index IDX_KDP_EMPLOYEES_ON_COMPANY on KDP_EMPLOYEES (COMPANY_ID);
create index IDX_KDP_EMPLOYEES_ON_DEPARTMENT on KDP_EMPLOYEES (DEPARTMENT_ID);
create index IDX_KDP_EMPLOYEES_ON_POSITION on KDP_EMPLOYEES (POSITION_ID);
create index IDX_KDP_EMPLOYEES_ON_MANAGER on KDP_EMPLOYEES (MANAGER_ID);
create index IDX_KDP_EMPLOYEES_ON_PHOTO on KDP_EMPLOYEES (PHOTO_ID);
-- end KDP_EMPLOYEES
-- begin KDP_POSITION
alter table KDP_POSITION add constraint FK_KDP_POSITION_ON_ORGANIZATIONS_ID foreign key (ORGANIZATIONS_ID_ID) references KDP_ORGANIZATIONS(ID);
create index IDX_KDP_POSITION_ON_ORGANIZATIONS_ID on KDP_POSITION (ORGANIZATIONS_ID_ID);
-- end KDP_POSITION
-- begin KDP_VACATION_REQUEST
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_EMPLOYEES foreign key (EMPLOYEES_ID) references KDP_EMPLOYEES(ID);
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_COMPANY foreign key (COMPANY_ID) references KDP_ORGANIZATIONS(ID);
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_DEPARTMENTS foreign key (DEPARTMENTS_ID) references KDP_DEPARTMENTS(ID);
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_POSITION foreign key (POSITION_ID) references KDP_POSITION(ID);
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_COORDINATOR foreign key (COORDINATOR_ID) references KDP_EMPLOYEES(ID);
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_VACATION_TYPE foreign key (VACATION_TYPE_ID) references KDP_VACATION_TYPE(ID);
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_DOCUMENT foreign key (DOCUMENT_ID) references SYS_FILE(ID);
alter table KDP_VACATION_REQUEST add constraint FK_KDP_VACATION_REQUEST_ON_INITIATOR foreign key (INITIATOR_ID) references KDP_EMPLOYEES(ID);
create unique index IDX_KDP_VACATION_REQUEST_UNIQ_APPLICATION_NUMBER on KDP_VACATION_REQUEST (APPLICATION_NUMBER);
create index IDX_KDP_VACATION_REQUEST_ON_EMPLOYEES on KDP_VACATION_REQUEST (EMPLOYEES_ID);
create index IDX_KDP_VACATION_REQUEST_ON_COMPANY on KDP_VACATION_REQUEST (COMPANY_ID);
create index IDX_KDP_VACATION_REQUEST_ON_DEPARTMENTS on KDP_VACATION_REQUEST (DEPARTMENTS_ID);
create index IDX_KDP_VACATION_REQUEST_ON_POSITION on KDP_VACATION_REQUEST (POSITION_ID);
create index IDX_KDP_VACATION_REQUEST_ON_COORDINATOR on KDP_VACATION_REQUEST (COORDINATOR_ID);
create index IDX_KDP_VACATION_REQUEST_ON_VACATION_TYPE on KDP_VACATION_REQUEST (VACATION_TYPE_ID);
create index IDX_KDP_VACATION_REQUEST_ON_DOCUMENT on KDP_VACATION_REQUEST (DOCUMENT_ID);
create index IDX_KDP_VACATION_REQUEST_ON_INITIATOR on KDP_VACATION_REQUEST (INITIATOR_ID);
-- end KDP_VACATION_REQUEST
