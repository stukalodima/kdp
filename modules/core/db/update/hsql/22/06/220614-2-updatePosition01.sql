-- alter table KDP_POSITION add column ORGANIZATIONS_ID_ID varchar(36) ^
-- update KDP_POSITION set ORGANIZATIONS_ID_ID = <default_value> ;
-- alter table KDP_POSITION alter column ORGANIZATIONS_ID_ID set not null ;
alter table KDP_POSITION add column ORGANIZATIONS_ID_ID varchar(36) not null ;
