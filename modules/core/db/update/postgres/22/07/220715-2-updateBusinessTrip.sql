alter table KDP_BUSINESS_TRIP rename column document_id to document_id__u38711 ;
alter table KDP_BUSINESS_TRIP drop constraint FK_KDP_BUSINESS_TRIP_ON_DOCUMENT ;
drop index IDX_KDP_BUSINESS_TRIP_ON_DOCUMENT ;
