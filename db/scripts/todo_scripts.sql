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

--changeset nikishin:add_priorities
CREATE TABLE priorities (
   id SERIAL PRIMARY KEY,
   name TEXT,
   position int
);

--changeset nikishin:insert_priorities
INSERT INTO priorities (name, position) VALUES ('urgently', 1);
INSERT INTO priorities (name, position) VALUES ('normal', 2);
INSERT INTO priorities (name, position) VALUES ('urgently', 3);

--changeset nikishin:add_column_priorities
ALTER TABLE tasks ADD COLUMN priority_id int REFERENCES priorities(id);

--changeset nikishin:update_tasks
UPDATE tasks SET priority_id = (SELECT id FROM priorities WHERE name = 'urgently' LIMIT 1);