alter table KDP_BUSINESS_TRIP add constraint FK_KDP_BUSINESS_TRIP_ON_ORGANIZATION foreign key (ORGANIZATION_ID) references KDP_ORGANIZATIONS(ID);
create index IDX_KDP_BUSINESS_TRIP_ON_ORGANIZATION on KDP_BUSINESS_TRIP (ORGANIZATION_ID);