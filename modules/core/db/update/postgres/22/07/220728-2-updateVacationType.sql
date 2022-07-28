alter table KDP_VACATION_TYPE rename column name to name__u30441 ;
alter table KDP_VACATION_TYPE alter column name__u30441 drop not null ;
alter table KDP_VACATION_TYPE add column NAME_RU varchar(255) ;
alter table KDP_VACATION_TYPE add column NAME_EN varchar(255) ;
alter table KDP_VACATION_TYPE add column NAME_UA varchar(255) ;
