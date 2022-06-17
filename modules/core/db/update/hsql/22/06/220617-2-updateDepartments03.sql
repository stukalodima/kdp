alter table KDP_DEPARTMENTS add constraint FK_KDP_DEPARTMENTS_ON_P_ID foreign key (P_ID_ID) references KDP_DEPARTMENTS(ID);
create index IDX_KDP_DEPARTMENTS_ON_P_ID on KDP_DEPARTMENTS (P_ID_ID);
