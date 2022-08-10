alter table KDP_OPERATOR_ACCESS_REQUEST rename column end_date to end_date__u70497 ;
alter table KDP_OPERATOR_ACCESS_REQUEST rename column start_date to start_date__u33626 ;
alter table KDP_OPERATOR_ACCESS_REQUEST add column START_DATE date ;
alter table KDP_OPERATOR_ACCESS_REQUEST add column END_DATE date ;
