-- begin KDP_EMPLOYEE
alter table KDP_EMPLOYEE add constraint FK_KDP_EMPLOYEE_ON_COMPANY foreign key (COMPANY_ID) references KDP_COMPANY(ID)^
create unique index IDX_KDP_EMPLOYEE_UNIQ_TAB_NUMBER on KDP_EMPLOYEE (TAB_NUMBER)^
create index IDX_KDP_EMPLOYEE_ON_COMPANY on KDP_EMPLOYEE (COMPANY_ID)^
-- end KDP_EMPLOYEE
-- begin KDP_CITY
alter table KDP_CITY add constraint FK_KDP_CITY_ON_REGION foreign key (REGION_ID) references KDP_REGION(ID)^
create index IDX_KDP_CITY_ON_REGION on KDP_CITY (REGION_ID)^
-- end KDP_CITY
-- begin KDP_REGION
create unique index IDX_KDP_REGION_UNIQ_NAME_REGION on KDP_REGION (NAME_REGION)^
-- end KDP_REGION
