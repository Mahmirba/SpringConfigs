INSERT IGNORE INTO role(id, name) VALUES (1, 'Admin');
INSERT IGNORE INTO role(id, name) VALUES (2, 'User');

INSERT IGNORE INTO privilege(id, name, uri) VALUES (1, 'STUDENT_CREATE', '/student/create');
INSERT IGNORE INTO privilege(id, name, uri) VALUES (2, 'STUDENT_FIND', '/student/find/{id}');
INSERT IGNORE INTO privilege(id, name, uri) VALUES (3, 'STUDENT_DELETE', '/student/delete/{id}');
INSERT IGNORE INTO privilege(id, name, uri) VALUES (4, 'STUDENT_SEARCH', '/student/search');

INSERT IGNORE INTO roles_privileges(role_id,privilege_id) VALUES (1, 1);
INSERT IGNORE INTO roles_privileges(role_id,privilege_id) VALUES (1, 2);
INSERT IGNORE INTO roles_privileges(role_id,privilege_id) VALUES (1, 3);
INSERT IGNORE INTO roles_privileges(role_id,privilege_id) VALUES (1, 4);

INSERT IGNORE INTO roles_privileges(role_id,privilege_id) VALUES (2, 2);
INSERT IGNORE INTO roles_privileges(role_id,privilege_id) VALUES (2, 4);
