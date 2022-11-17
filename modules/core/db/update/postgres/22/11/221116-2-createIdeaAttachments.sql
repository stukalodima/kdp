alter table KDP_IDEA_ATTACHMENTS add constraint FK_KDP_IDEA_ATTACHMENTS_ON_DOCUMENT foreign key (DOCUMENT_ID) references SYS_FILE(ID);
alter table KDP_IDEA_ATTACHMENTS add constraint FK_KDP_IDEA_ATTACHMENTS_ON_AUTHOR foreign key (AUTHOR_ID) references KDP_EMPLOYEES(ID);
alter table KDP_IDEA_ATTACHMENTS add constraint FK_KDP_IDEA_ATTACHMENTS_ON_IDEA foreign key (IDEA_ID) references KDP_IDEA(ID);
create index IDX_KDP_IDEA_ATTACHMENTS_ON_DOCUMENT on KDP_IDEA_ATTACHMENTS (DOCUMENT_ID);
create index IDX_KDP_IDEA_ATTACHMENTS_ON_AUTHOR on KDP_IDEA_ATTACHMENTS (AUTHOR_ID);
create index IDX_KDP_IDEA_ATTACHMENTS_ON_IDEA on KDP_IDEA_ATTACHMENTS (IDEA_ID);
