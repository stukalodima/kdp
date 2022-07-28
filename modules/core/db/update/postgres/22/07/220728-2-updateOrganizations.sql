alter table KDP_ORGANIZATIONS rename column short_name to short_name__u34372 ;
alter table KDP_ORGANIZATIONS alter column short_name__u34372 drop not null ;
alter table KDP_ORGANIZATIONS rename column prefix to prefix__u85498 ;
alter table KDP_ORGANIZATIONS alter column CODE set data type varchar(255) ;
alter table KDP_ORGANIZATIONS alter column FULL_NAME drop not null ;
alter table KDP_ORGANIZATIONS alter column ORGANIZATIONS_UA drop not null ;
alter table KDP_ORGANIZATIONS alter column ORGANIZATIONS_RU drop not null ;
