alter table KDP_ORGANIZATIONS rename column organizations1c_id to organizations1c_id__u60762 ;
alter table KDP_ORGANIZATIONS alter column CODE drop not null ;
alter table KDP_ORGANIZATIONS alter column PREFIX drop not null ;
alter table KDP_ORGANIZATIONS alter column ORGANIZATIONS_EN drop not null ;
alter table KDP_ORGANIZATIONS alter column CODE_OCPO drop not null ;
alter table KDP_ORGANIZATIONS add column ORGANIZATIONS1C_ID varchar(255) ;
alter table KDP_ORGANIZATIONS alter column COUNTRY_REGISTRATION_ID drop not null ;
