alter table KDP_ORGANIZATIONS add column CODE_OCPO varchar(12) ^
update KDP_ORGANIZATIONS set CODE_OCPO = '' where CODE_OCPO is null ;
alter table KDP_ORGANIZATIONS alter column CODE_OCPO set not null ;
