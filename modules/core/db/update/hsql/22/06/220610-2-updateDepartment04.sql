alter table KDP_DEPARTMENT add constraint FK_KDP_DEPARTMENT_ON_P_ID foreign key (P_ID_ID) references KDP_DEPARTMENT(ID);
create index IDX_KDP_DEPARTMENT_ON_P_ID on KDP_DEPARTMENT (P_ID_ID);
