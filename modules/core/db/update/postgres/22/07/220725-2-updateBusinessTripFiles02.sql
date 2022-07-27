alter table KDP_BUSINESS_TRIP_FILES add constraint FK_KDP_BUSINESS_TRIP_FILES_ON_AUTHOR foreign key (AUTHOR_ID) references KDP_EMPLOYEES(ID);
create index IDX_KDP_BUSINESS_TRIP_FILES_ON_AUTHOR on KDP_BUSINESS_TRIP_FILES (AUTHOR_ID);