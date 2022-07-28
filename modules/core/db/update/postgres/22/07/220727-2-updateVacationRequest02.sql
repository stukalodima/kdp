alter table KDP_VACATION_REQUEST rename column document_id to document_id__u23136 ;
alter table KDP_VACATION_REQUEST drop constraint FK_KDP_VACATION_REQUEST_ON_DOCUMENT ;
drop index IDX_KDP_VACATION_REQUEST_ON_DOCUMENT ;
alter table KDP_VACATION_REQUEST add column PROC_INSTANCE_ID uuid ;
alter table KDP_VACATION_REQUEST add column PHONE_NUMBER varchar(255) ;
