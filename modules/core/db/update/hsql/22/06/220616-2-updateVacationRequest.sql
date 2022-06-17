alter table KDP_VACATION_REQUEST alter column CODE rename to CODE__U00048 ^
alter table KDP_VACATION_REQUEST alter column CODE__U00048 set null ;
-- alter table KDP_VACATION_REQUEST add column APPLICATION_NUMBER integer ^
-- update KDP_VACATION_REQUEST set APPLICATION_NUMBER = <default_value> ;
-- alter table KDP_VACATION_REQUEST alter column APPLICATION_NUMBER set not null ;
alter table KDP_VACATION_REQUEST add column APPLICATION_NUMBER integer ;
