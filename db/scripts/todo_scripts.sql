--liquibase formatted sql

--changeset nikishin:Ucreate_tasks
CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);

--changeset nikishin:create_user
create table if not exists users (
    id serial primary key,
    name varchar(255),
    login varchar(255) unique,
    password varchar(255)
);

--changeset nikishin:update_task_add_column
alter table tasks add column id_user int references users(id);