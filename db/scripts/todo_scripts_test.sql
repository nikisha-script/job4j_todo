--liquibase formatted sql

--changeset nikishin:create_tasks_test
CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);