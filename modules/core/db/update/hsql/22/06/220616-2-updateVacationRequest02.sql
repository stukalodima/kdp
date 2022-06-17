-- update KDP_VACATION_REQUEST set APPLICATION_NUMBER = <default_value> where APPLICATION_NUMBER is null ;
alter table KDP_VACATION_REQUEST alter column APPLICATION_NUMBER set not null ;
