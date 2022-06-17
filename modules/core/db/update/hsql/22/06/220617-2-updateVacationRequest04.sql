alter table KDP_VACATION_REQUEST add column DOCUMENT_ID varchar(36) ;
-- update KDP_VACATION_REQUEST set DEPARTMENTS_ID = <default_value> where DEPARTMENTS_ID is null ;
alter table KDP_VACATION_REQUEST alter column DEPARTMENTS_ID set not null ;
-- update KDP_VACATION_REQUEST set POSITION_ID = <default_value> where POSITION_ID is null ;
alter table KDP_VACATION_REQUEST alter column POSITION_ID set not null ;
