alter table KDP_ORGANIZATIONS alter column CODE_REGISTRATION rename to CODE_REGISTRATION__U20228 ^
alter table KDP_ORGANIZATIONS alter column CODE_REGISTRATION__U20228 set null ;
alter table KDP_ORGANIZATIONS add column CODE_OCPO varchar(12) ^
update KDP_ORGANIZATIONS set CODE_OCPO = '' where CODE_OCPO is null ;
alter table KDP_ORGANIZATIONS alter column CODE_OCPO set not null ;
