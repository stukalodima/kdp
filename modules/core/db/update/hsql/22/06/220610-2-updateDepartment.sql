alter table KDP_DEPARTMENT alter column DEPARTMENT_EN rename to DEPARTMENT_EN__U90020 ^
alter table KDP_DEPARTMENT alter column DEPARTMENT_EN__U90020 set null ;
alter table KDP_DEPARTMENT alter column DEPARTMENT_RU rename to DEPARTMENT_RU__U28274 ^
alter table KDP_DEPARTMENT alter column DEPARTMENT_RU__U28274 set null ;
alter table KDP_DEPARTMENT alter column P_ID_ID rename to P_ID_ID__U94225 ^
alter table KDP_DEPARTMENT drop constraint FK_KDP_DEPARTMENT_ON_P_ID ;
drop index IDX_KDP_DEPARTMENT_ON_P_ID ;
alter table KDP_DEPARTMENT alter column MANAGER_ID_ID rename to MANAGER_ID_ID__U14281 ^
alter table KDP_DEPARTMENT drop constraint FK_KDP_DEPARTMENT_ON_MANAGER_ID ;
drop index IDX_KDP_DEPARTMENT_ON_MANAGER_ID ;
alter table KDP_DEPARTMENT alter column DEPARTMENT_UA rename to DEPARTMENT_UA__U99098 ^
alter table KDP_DEPARTMENT alter column DEPARTMENT_UA__U99098 set null ;
alter table KDP_DEPARTMENT alter column APPROVAL_MANAGER_ID rename to APPROVAL_MANAGER_ID__U52242 ^
alter table KDP_DEPARTMENT drop constraint FK_KDP_DEPARTMENT_ON_APPROVAL_MANAGER ;
drop index IDX_KDP_DEPARTMENT_ON_APPROVAL_MANAGER ;
