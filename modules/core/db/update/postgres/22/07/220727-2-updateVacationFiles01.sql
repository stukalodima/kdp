alter table KDP_VACATION_FILES add constraint FK_KDP_VACATION_FILES_ON_VACATION foreign key (VACATION_ID) references KDP_VACATION_REQUEST(ID);
create index IDX_KDP_VACATION_FILES_ON_VACATION on KDP_VACATION_FILES (VACATION_ID);