alter table KDP_VACATION_FILES rename column vacation_id to vacation_id__u32992 ;
alter table KDP_VACATION_FILES drop constraint FK_KDP_VACATION_FILES_ON_VACATION ;
drop index IDX_KDP_VACATION_FILES_ON_VACATION ;
alter table KDP_VACATION_FILES add column VACATION_ID uuid ;
