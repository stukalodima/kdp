alter table KDP_ORGANIZATIONS add column ACTIVE boolean ;
alter table KDP_ORGANIZATIONS add column ORGANIZATIONS_EN varchar(255) ^
update KDP_ORGANIZATIONS set ORGANIZATIONS_EN = '' where ORGANIZATIONS_EN is null ;
alter table KDP_ORGANIZATIONS alter column ORGANIZATIONS_EN set not null ;
alter table KDP_ORGANIZATIONS add column ORGANIZATIONS1C_ID varchar(36) ;
alter table KDP_ORGANIZATIONS add column ORGANIZATIONS_RU varchar(255) ^
update KDP_ORGANIZATIONS set ORGANIZATIONS_RU = '' where ORGANIZATIONS_RU is null ;
alter table KDP_ORGANIZATIONS alter column ORGANIZATIONS_RU set not null ;
alter table KDP_ORGANIZATIONS add column ORGANIZATIONS_UA varchar(255) ^
update KDP_ORGANIZATIONS set ORGANIZATIONS_UA = '' where ORGANIZATIONS_UA is null ;
alter table KDP_ORGANIZATIONS alter column ORGANIZATIONS_UA set not null ;
