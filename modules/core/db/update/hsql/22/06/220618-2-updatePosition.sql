alter table KDP_POSITION add column POSITION_RU varchar(255) ^
update KDP_POSITION set POSITION_RU = '' where POSITION_RU is null ;
alter table KDP_POSITION alter column POSITION_RU set not null ;
-- alter table KDP_POSITION add column ORGANIZATIONS_ID_ID varchar(36) ^
-- update KDP_POSITION set ORGANIZATIONS_ID_ID = <default_value> ;
-- alter table KDP_POSITION alter column ORGANIZATIONS_ID_ID set not null ;
alter table KDP_POSITION add column ORGANIZATIONS_ID_ID varchar(36) not null ;
alter table KDP_POSITION add column POSITION_UA varchar(255) ^
update KDP_POSITION set POSITION_UA = '' where POSITION_UA is null ;
alter table KDP_POSITION alter column POSITION_UA set not null ;
alter table KDP_POSITION add column POSITION_EN varchar(255) ^
update KDP_POSITION set POSITION_EN = '' where POSITION_EN is null ;
alter table KDP_POSITION alter column POSITION_EN set not null ;
update KDP_POSITION set NAME = '' where NAME is null ;
alter table KDP_POSITION alter column NAME set not null ;
