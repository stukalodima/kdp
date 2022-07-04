alter table KDP_EMPLOYEES alter column DEPARTMENT_ID rename to DEPARTMENT_ID__U24428 ^
alter table KDP_EMPLOYEES alter column DEPARTMENT_ID__U24428 set null ;
drop index IDX_KDP_EMPLOYEES_ON_DEPARTMENT ;
-- alter table KDP_EMPLOYEES add column DEPARTMENT_ID varchar(36) ^
-- update KDP_EMPLOYEES set DEPARTMENT_ID = <default_value> ;
-- alter table KDP_EMPLOYEES alter column DEPARTMENT_ID set not null ;
alter table KDP_EMPLOYEES add column DEPARTMENT_ID varchar(36) not null ;
