alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_VACATION_MANAGER foreign key (VACATION_MANAGER_ID) references KDP_EMPLOYEES(ID);
create index IDX_KDP_EMPLOYEES_ON_VACATION_MANAGER on KDP_EMPLOYEES (VACATION_MANAGER_ID);
