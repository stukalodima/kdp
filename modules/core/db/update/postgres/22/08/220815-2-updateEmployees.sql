alter table KDP_EMPLOYEES rename column vacation_manager_id to vacation_manager_id__u71884 ;
alter table KDP_EMPLOYEES drop constraint FK_KDP_EMPLOYEES_ON_VACATION_MANAGER ;
drop index IDX_KDP_EMPLOYEES_ON_VACATION_MANAGER ;
alter table KDP_EMPLOYEES rename column approval_manager_id to approval_manager_id__u42442 ;
alter table KDP_EMPLOYEES drop constraint FK_KDP_EMPLOYEES_ON_APPROVAL_MANAGER ;
drop index IDX_KDP_EMPLOYEES_ON_APPROVAL_MANAGER ;
