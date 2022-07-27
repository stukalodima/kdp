alter table KDP_DEPARTMENTS add column DEPARTMENT_1C_ID varchar(255) ;
alter table KDP_DEPARTMENTS alter column DEPARTMENT_EN drop not null ;
alter table KDP_DEPARTMENTS alter column DEPARTMENT_UA drop not null ;
alter table KDP_DEPARTMENTS alter column DEPARTMENT_RU drop not null ;
