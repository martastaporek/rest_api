create table AbstractEntity (id  bigserial not null, primary key (id))
create table Player (birthYear int4 not null, firstName varchar(255), lastName varchar(255), id int8 not null, primary key (id))
alter table Player add constraint FKt5lbj1n3rk8mjjvaqbo031ons foreign key (id) references AbstractEntity
create table AbstractEntity (id  bigserial not null, primary key (id))
create table Player (birthYear int4 not null, firstName varchar(255), lastName varchar(255), id int8 not null, primary key (id))
alter table Player add constraint FKt5lbj1n3rk8mjjvaqbo031ons foreign key (id) references AbstractEntity
create table AbstractEntity (id  bigserial not null, primary key (id))
create table Player (birthYear int4 not null, firstName varchar(255), lastName varchar(255), id int8 not null, primary key (id))
alter table Player add constraint FKt5lbj1n3rk8mjjvaqbo031ons foreign key (id) references AbstractEntity
create table match (date date, firstTeamScore int4 not null, location varchar(255), secondTeamScore int4 not null, id int8 not null, firstTeam_id int8, secondTeam_id int8, primary key (id))
alter table match add constraint FKp4xjbd4769o1v8h1r1epif2b foreign key (firstTeam_id) references team
alter table match add constraint FKrkqc0aw4r1v153iuv7dvuv49n foreign key (secondTeam_id) references team
alter table match add constraint FKl5imm4n63k9ycorm87bg440jh foreign key (id) references AbstractEntity
create table match (date date, firstTeamScore int4 not null, location varchar(255), secondTeamScore int4 not null, id int8 not null, firstTeam_id int8, secondTeam_id int8, primary key (id))
alter table public.player add column team_id int8
create table team (name varchar(255), id int8 not null, primary key (id))
alter table match add constraint FKp4xjbd4769o1v8h1r1epif2b foreign key (firstTeam_id) references team
alter table match add constraint FKrkqc0aw4r1v153iuv7dvuv49n foreign key (secondTeam_id) references team
alter table match add constraint FKl5imm4n63k9ycorm87bg440jh foreign key (id) references AbstractEntity
alter table player add constraint FKdvd6ljes11r44igawmpm1mc5s foreign key (team_id) references team
alter table player add constraint FKpqcusuem3epgvcsf8x7xv8gig foreign key (id) references AbstractEntity
alter table team add constraint FKtqbm9jpib5h42j7chcs9bm7qb foreign key (id) references AbstractEntity
