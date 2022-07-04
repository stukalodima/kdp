alter table KDP_EMPLOYEES add constraint FK_KDP_EMPLOYEES_ON_DEPARTMENT foreign key (DEPARTMENT_ID) references KDP_DEPARTMENTS(ID);
