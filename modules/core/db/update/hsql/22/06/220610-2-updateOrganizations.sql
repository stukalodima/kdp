alter table KDP_ORGANIZATIONS add column COUNTRY_REGISTRATION varchar(255) ;
alter table KDP_ORGANIZATIONS add column DATE_REGISTRATION date ;
alter table KDP_ORGANIZATIONS add column CODE varchar(9) ^
update KDP_ORGANIZATIONS set CODE = '' where CODE is null ;
alter table KDP_ORGANIZATIONS alter column CODE set not null ;
alter table KDP_ORGANIZATIONS add column CODE_OCPO varchar(12) ^
update KDP_ORGANIZATIONS set CODE_OCPO = '' where CODE_OCPO is null ;
alter table KDP_ORGANIZATIONS alter column CODE_OCPO set not null ;
alter table KDP_ORGANIZATIONS add column PREFIX varchar(3) ^
update KDP_ORGANIZATIONS set PREFIX = '' where PREFIX is null ;
alter table KDP_ORGANIZATIONS alter column PREFIX set not null ;
alter table KDP_ORGANIZATIONS add column ENTITY boolean ;
alter table KDP_ORGANIZATIONS add column FULL_NAME varchar(255) ^
update KDP_ORGANIZATIONS set FULL_NAME = '' where FULL_NAME is null ;
alter table KDP_ORGANIZATIONS alter column FULL_NAME set not null ;
