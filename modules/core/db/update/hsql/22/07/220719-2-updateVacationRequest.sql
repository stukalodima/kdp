alter table KDP_VACATION_REQUEST alter column PROC_INSTANCE_ID rename to PROC_INSTANCE_ID__U00628 ^
alter table KDP_VACATION_REQUEST drop constraint FK_KDP_VACATION_REQUEST_ON_PROC_INSTANCE ;
drop index IDX_KDP_VACATION_REQUEST_ON_PROC_INSTANCE ;