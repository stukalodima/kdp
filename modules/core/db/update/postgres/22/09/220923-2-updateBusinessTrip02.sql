alter table KDP_BUSINESS_TRIP rename column initiator_id to initiator_id__u66633 ;
alter table KDP_BUSINESS_TRIP drop constraint FK_KDP_BUSINESS_TRIP_ON_INITIATOR ;
drop index IDX_KDP_BUSINESS_TRIP_ON_INITIATOR ;
