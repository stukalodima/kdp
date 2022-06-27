-- alter table KDP_ORGANIZATIONS add column COUNTRY_REGISTRATION_ID varchar(36) ^
-- update KDP_ORGANIZATIONS set COUNTRY_REGISTRATION_ID = <default_value> ;
-- alter table KDP_ORGANIZATIONS alter column COUNTRY_REGISTRATION_ID set not null ;
alter table KDP_ORGANIZATIONS add column COUNTRY_REGISTRATION_ID varchar(36) not null ;
