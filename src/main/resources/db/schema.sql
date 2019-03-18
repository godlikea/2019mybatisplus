CREATE TABLE  t_user (id INT PRIMARY KEY, name VARCHAR(64));

CREATE TABLE  t_role (id INT PRIMARY KEY, role VARCHAR(64));

CREATE TABLE  t_permission (id INT PRIMARY KEY, per VARCHAR(64));

CREATE TABLE  t_user_role (uid INT,rid INT);

CREATE TABLE  t_role_permission (rid INT,pid INT);
