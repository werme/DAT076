# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        bigint not null,
  author                    varchar(255),
  posted_at                 timestamp,
  content                   varchar(255),
  note_id                   bigint,
  constraint pk_comment primary key (id))
;

create table note (
  id                        bigint not null,
  title                     varchar(255),
  text                      varchar(255),
  author                    varchar(255),
  constraint pk_note primary key (id))
;

create table tag (
  id                        bigint not null,
  title                     varchar(255),
  constraint uq_tag_1 unique (title),
  constraint pk_tag primary key (id))
;

create table user (
  email                     varchar(255) not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint uq_user_1 unique (username),
  constraint pk_user primary key (email))
;


create table note_tag (
  note_id                        bigint not null,
  tag_id                         bigint not null,
  constraint pk_note_tag primary key (note_id, tag_id))
;
create sequence comment_seq;

create sequence note_seq;

create sequence tag_seq;

create sequence user_seq;

alter table comment add constraint fk_comment_note_1 foreign key (note_id) references note (id) on delete restrict on update restrict;
create index ix_comment_note_1 on comment (note_id);



alter table note_tag add constraint fk_note_tag_note_01 foreign key (note_id) references note (id) on delete restrict on update restrict;

alter table note_tag add constraint fk_note_tag_tag_02 foreign key (tag_id) references tag (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

drop table if exists note;

drop table if exists note_tag;

drop table if exists tag;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comment_seq;

drop sequence if exists note_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_seq;

