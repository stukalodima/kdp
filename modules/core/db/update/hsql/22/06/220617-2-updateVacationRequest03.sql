-- update KDP_VACATION_REQUEST set COORDINATOR_ID = <default_value> where COORDINATOR_ID is null ;
alter table KDP_VACATION_REQUEST alter column COORDINATOR_ID set not null ;
