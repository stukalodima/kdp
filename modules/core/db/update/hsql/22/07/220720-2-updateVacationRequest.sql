alter table KDP_VACATION_REQUEST alter column INITIATOR_ID rename to INITIATOR_ID__U09916 ^
alter table KDP_VACATION_REQUEST drop constraint FK_KDP_VACATION_REQUEST_ON_INITIATOR ;
drop index IDX_KDP_VACATION_REQUEST_ON_INITIATOR ;
alter table KDP_VACATION_REQUEST add column INITIATOR_ID varchar(36) ;
