alter table KDP_EMPLOYEE add constraint FK_KDP_EMPLOYEE_ON_STREET foreign key (STREET_ID) references KDP_STREETS(ID);
create index IDX_KDP_EMPLOYEE_ON_STREET on KDP_EMPLOYEE (STREET_ID);
