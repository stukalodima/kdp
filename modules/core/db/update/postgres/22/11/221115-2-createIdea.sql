alter table KDP_IDEA add constraint FK_KDP_IDEA_ON_AUTHOR foreign key (AUTHOR_ID) references KDP_EMPLOYEES(ID);
alter table KDP_IDEA add constraint FK_KDP_IDEA_ON_MANAGER foreign key (MANAGER_ID) references KDP_EMPLOYEES(ID);
alter table KDP_IDEA add constraint FK_KDP_IDEA_ON_EXECUTOR foreign key (EXECUTOR_ID) references KDP_EMPLOYEES(ID);
create index IDX_KDP_IDEA_ON_AUTHOR on KDP_IDEA (AUTHOR_ID);
create index IDX_KDP_IDEA_ON_MANAGER on KDP_IDEA (MANAGER_ID);
create index IDX_KDP_IDEA_ON_EXECUTOR on KDP_IDEA (EXECUTOR_ID);
