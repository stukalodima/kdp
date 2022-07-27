alter table KDP_BUSINESS_TRIP_FILES add constraint FK_KDP_BUSINESS_TRIP_FILES_ON_DOCUMENT foreign key (DOCUMENT_ID) references SYS_FILE(ID);
alter table KDP_BUSINESS_TRIP_FILES add constraint FK_KDP_BUSINESS_TRIP_FILES_ON_BUSINESS_TRIP foreign key (BUSINESS_TRIP_ID) references KDP_BUSINESS_TRIP(ID);
create index IDX_KDP_BUSINESS_TRIP_FILES_ON_DOCUMENT on KDP_BUSINESS_TRIP_FILES (DOCUMENT_ID);
create index IDX_KDP_BUSINESS_TRIP_FILES_ON_BUSINESS_TRIP on KDP_BUSINESS_TRIP_FILES (BUSINESS_TRIP_ID);
