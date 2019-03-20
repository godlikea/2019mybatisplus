/*用户*/
INSERT INTO t_user (id, name,pwd) VALUES (1, '张三','123');
INSERT INTO t_user (id, name,pwd) VALUES (2, '李四','123');
INSERT INTO t_user (id, name,pwd) VALUES (3, '王五','123');

/*角色*/
INSERT INTO t_role (id, role) VALUES (1, '经理');
INSERT INTO t_role (id, role) VALUES (2, '总监');
INSERT INTO t_role (id, role) VALUES (3, '研发');

/*权限*/
INSERT INTO t_permission (id, per) VALUES (1, '添加');
INSERT INTO t_permission (id, per) VALUES (2, '删除');
INSERT INTO t_permission (id, per) VALUES (3, '修改');

/*用户-角色*/
INSERT INTO t_user_role (uid, rid) VALUES (1, 1);
INSERT INTO t_user_role (uid, rid) VALUES (1, 3);
INSERT INTO t_user_role (uid, rid) VALUES (2, 2);

/*角色-权限*/
INSERT INTO t_role_permission (rid, pid) VALUES (1, 1);
INSERT INTO t_role_permission (rid, pid) VALUES (1, 2);
INSERT INTO t_role_permission (rid, pid) VALUES (1, 3);