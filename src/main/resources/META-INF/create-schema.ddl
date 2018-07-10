create table AbstractEntity (id  bigserial not null, primary key (id))
create table Player (birthYear int4 not null, firstName varchar(255), lastName varchar(255), id int8 not null, primary key (id))
alter table Player add constraint FKt5lbj1n3rk8mjjvaqbo031ons foreign key (id) references AbstractEntity
create table AbstractEntity (id  bigserial not null, primary key (id))
create table Player (birthYear int4 not null, firstName varchar(255), lastName varchar(255), id int8 not null, primary key (id))
alter table Player add constraint FKt5lbj1n3rk8mjjvaqbo031ons foreign key (id) references AbstractEntity
create table AbstractEntity (id  bigserial not null, primary key (id))
create table Player (birthYear int4 not null, firstName varchar(255), lastName varchar(255), id int8 not null, primary key (id))
alter table Player add constraint FKt5lbj1n3rk8mjjvaqbo031ons foreign key (id) references AbstractEntity
