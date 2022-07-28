alter table KDP_COUNTRIES rename column name to name__u75746 ;
alter table KDP_COUNTRIES alter column name__u75746 drop not null ;
alter table KDP_COUNTRIES add column NAME_RU varchar(255) ;
alter table KDP_COUNTRIES add column NAME_EN varchar(255) ;
alter table KDP_COUNTRIES add column NAME_UA varchar(255) ;
alter table KDP_COUNTRIES alter column CODE drop not null ;
alter table KDP_COUNTRIES alter column FULL_NAME drop not null ;
