alter table KDP_EMPLOYEES rename column employee_1c_id to employee_1c_id__u20518 ;
alter table KDP_EMPLOYEES alter column SURNAME_RU drop not null ;
alter table KDP_EMPLOYEES alter column NAME_RU drop not null ;
alter table KDP_EMPLOYEES alter column MIDDLE_NAME_RU drop not null ;
alter table KDP_EMPLOYEES alter column SURNAME_EN drop not null ;
alter table KDP_EMPLOYEES alter column NAME_EN drop not null ;
alter table KDP_EMPLOYEES alter column WORK_EMAIL drop not null ;
alter table KDP_EMPLOYEES alter column LOGIN_NAME drop not null ;
alter table KDP_EMPLOYEES add column EMPLOYEE_1C_ID varchar(255) ;
