alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_APPROVAL_MANAGER foreign key (APPROVAL_MANAGER_ID) references SEC_USER(ID);
create index IDX_KDP_DEPARTMENTS_ON_APPROVAL_MANAGER on KDP_DEPARTMENTS (APPROVAL_MANAGER_ID);
