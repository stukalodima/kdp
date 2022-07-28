alter table KDP_DEPARTMENTS rename column department_id to department_id__u21818 ;
alter table KDP_DEPARTMENTS rename column name to name__u22825 ;
alter table KDP_DEPARTMENTS alter column name__u22825 drop not null ;
alter table KDP_DEPARTMENTS alter column CODE drop not null ;
alter table KDP_DEPARTMENTS alter column ORGANIZATION_ID drop not null ;
