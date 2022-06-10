alter table KDP_DEPARTMENT add column APPROVAL_MANAGER_ID varchar(36) ;
alter table KDP_DEPARTMENT add column DEPARTMENT_UA varchar(255) ^
update KDP_DEPARTMENT set DEPARTMENT_UA = '' where DEPARTMENT_UA is null ;
alter table KDP_DEPARTMENT alter column DEPARTMENT_UA set not null ;
alter table KDP_DEPARTMENT add column MANAGER_ID_ID varchar(36) ;
alter table KDP_DEPARTMENT add column DEPARTMENT_ID varchar(36) ^
update KDP_DEPARTMENT set DEPARTMENT_ID = '00000000-0000-0000-0000-000000000000' where DEPARTMENT_ID is null ;
alter table KDP_DEPARTMENT alter column DEPARTMENT_ID set not null ;
alter table KDP_DEPARTMENT add column P_ID_ID varchar(36) ;
alter table KDP_DEPARTMENT add column DEPARTMENT_RU varchar(255) ^
update KDP_DEPARTMENT set DEPARTMENT_RU = '' where DEPARTMENT_RU is null ;
alter table KDP_DEPARTMENT alter column DEPARTMENT_RU set not null ;
alter table KDP_DEPARTMENT add column DEPARTMENT_EN varchar(255) ^
update KDP_DEPARTMENT set DEPARTMENT_EN = '' where DEPARTMENT_EN is null ;
alter table KDP_DEPARTMENT alter column DEPARTMENT_EN set not null ;
