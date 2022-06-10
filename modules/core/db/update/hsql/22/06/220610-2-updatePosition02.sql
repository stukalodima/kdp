alter table KDP_POSITION alter column POSITION_EN rename to POSITION_EN__U80199 ^
alter table KDP_POSITION alter column POSITION_EN__U80199 set null ;
alter table KDP_POSITION alter column POSITION_UA rename to POSITION_UA__U66260 ^
alter table KDP_POSITION alter column POSITION_UA__U66260 set null ;
alter table KDP_POSITION alter column COMPANY_ID_ID rename to COMPANY_ID_ID__U21356 ^
alter table KDP_POSITION alter column COMPANY_ID_ID__U21356 set null ;
alter table KDP_POSITION drop constraint FK_KDP_POSITION_ON_COMPANY_ID ;
drop index IDX_KDP_POSITION_ON_COMPANY_ID ;
alter table KDP_POSITION alter column POSITION_RU rename to POSITION_RU__U49228 ^
alter table KDP_POSITION alter column POSITION_RU__U49228 set null ;
