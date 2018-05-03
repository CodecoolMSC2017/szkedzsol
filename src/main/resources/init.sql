/*
    Database initialization script that runs on every web-application redeployment.
*/
DROP TABLE if EXISTS slot_tasks;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS slot;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS col;
DROP TABLE IF EXISTS users;




CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    role TEXT NOT NULL
);



CREATE TABLE schedule (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
	name TEXT NOT NULL,
	col_id INTEGER,
	FOREIGN KEY(user_id)REFERENCES users(id)
);
CREATE TABLE col (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
	schedule_id INTEGER ,
	FOREIGN KEY(schedule_id) REFERENCES schedule(id)
);





CREATE TABLE slot (
    id SERIAL PRIMARY KEY,
    col_id INTEGER NOT NULL,
    start INTEGER NOT NULL CHECK (start>=0 AND start <24),
    stop INTEGER NOT NULL CHECK (stop>=0 AND stop <24),
    FOREIGN KEY (col_id) REFERENCES col(id)
);

CREATE TABLE task (
    id SERIAL PRIMARY KEY,
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
	(1,'user1','user1@user1','ADMIN'), -- 1
	(2,'user2','user2@user2','REGISTERED'), -- 2
	(3,'user3','user3@user3','GUEST'); -- 3

INSERT INTO col(id,name)VALUES
    (1,'Pisti'),
    (2,'Itvan');

INSERT INTO schedule (id,user_id,name, col_id) VALUES
	(1, 1, 'napirend', 1),   -- 1
	(2 , 2, 'blabla', 1),-- 2
	(3 , 3, 'blabla2', 2);-- 2



INSERT INTO slot(id,col_id,start,stop)VALUES
    (1,1,1,2),
    (2,2,3,4);



INSERT INTO task(id,description,user_id)VALUES
    (1,'kutya simogatas',001),
    (2,'kutya setaltatas',002);




