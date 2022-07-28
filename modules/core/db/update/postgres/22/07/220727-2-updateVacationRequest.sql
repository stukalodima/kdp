alter table KDP_VACATION_REQUEST rename column initiator_id to initiator_id__u92646 ;
alter table KDP_VACATION_REQUEST drop constraint FK_KDP_VACATION_REQUEST_ON_INITIATOR ;
drop index IDX_KDP_VACATION_REQUEST_ON_INITIATOR ;
alter table KDP_VACATION_REQUEST add column INITIATOR_ID uuid ;
