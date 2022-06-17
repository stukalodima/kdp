update KDP_POSITION set NAME = '' where NAME is null ;
alter table KDP_POSITION alter column NAME set not null ;
