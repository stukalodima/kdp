alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_USER foreign key (USER_ID) references SEC_USER(ID);
create index IDX_KDP_EMPLOYEES_ON_USER on KDP_EMPLOYEES (USER_ID);
