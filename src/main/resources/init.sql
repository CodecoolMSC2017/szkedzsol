/*
    Database initialization script that runs on every web-application redeployment.
*/
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS slot;
DROP TABLE IF EXISTS col;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS users;
DROP TABLE if EXISTS slot_tasks;


CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    role TEXT NOT NULL,
);

CREATE TABLE schedule (
    id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
	name TEXT NOT NULL,
	col_id INTEGER,
    FOREIGN KEY(col_id)REFERENCES col(id),
	FOREIGN KEY(user_id)REFERENCES users(id)
);

CREATE TABLE col (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE slot (
    id INTEGER PRIMARY KEY,
    col_id INTEGER NOT NULL,
    start INTEGER NOT NULL CHECK (start>=0 AND start <24),
    stop INTEGER NOT NULL CHECK (stop>=0 AND stop <24),
    FOREIGN KEY (col_id) REFERENCES col(id)
);
CREATE TABLE task (
    id INTEGER PRIMARY KEY,
    description TEXT NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY(user_id)REFERENCES users(id)
);

CREATE TABLE slot_tasks(
    slot_id INTEGER,
    task_id INTEGER,
    FOREIGN KEY(slot_id)REFERENCES slot(id),
    FOREIGN KEY(task_id)REFERENCES task(id)
);




INSERT INTO users (id,name,email,role) VALUES
	(001,'user1','user1@user1','ADMIN'), -- 1
	(002,'user2','user2@user2','REGISTERED'), -- 2
	(003,'user3','user3@user3','GUEST'); -- 3

INSERT INTO schedule (id,user_id,name) VALUES
	(1,001,'napirend'),   -- 1
	(2 ,002,'blabla'),-- 2
	(3 ,003,'blabla2');-- 2

INSERT INTO col(id,name)VALUES
    (01,'Pisti'),
    (02,'Itvan');

INSERT INTO slot(id,col_id,start,stop,)VALUES
    (1,01,1,2,),
    (2,02,3,4);




