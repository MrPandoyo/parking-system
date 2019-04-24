INSERT INTO user (id, active, email, fullname, hp, username, user_type) VALUES
('admin', true, 'admin@yopmail.com', 'Administrator', '02185655', 'admin@yopmail.com', 'ADMIN');

INSERT INTO user_password (id_user, password) VALUES
('admin', '$2a$08$LS3sz9Ft014MNaIGCEyt4u6VflkslOW/xosyRbinIF9.uaVLpEhB6');

-- for admin
INSERT INTO permission (id, permission_label, permission_value) VALUES
  ('001_admin', 'View dummy admin', 'ROLE_VIEW_DUMMY');

-- for client
INSERT INTO permission (id, permission_label, permission_value) VALUES
  ('001_client', 'View Dummy client', 'ROLE_CLIENT_VIEW_DUMMY');

INSERT INTO user_permission (id_user, id_permission) VALUES
  ('admin','001_admin');

