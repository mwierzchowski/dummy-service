--liquibase formatted sql

--changeset mwierzchowski:create-dummy-table
create sequence dummy_id_seq increment by 10;

create table dummy (
    id int primary key,
    name varchar(30) unique not null,
    age int,
    created timestamp not null,
    updated timestamp not null,
    version int not null
);