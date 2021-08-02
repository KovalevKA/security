INSERT INTO roles (name, status, created, updated)
VALUES ('ROLE_ADMIN', 'ACTIVE', CURRENT_DATE, CURRENT_DATE);
INSERT INTO roles (name, status, created, updated)
VALUES ('ROLE_USER', 'ACTIVE', CURRENT_DATE, CURRENT_DATE);

INSERT INTO users(username, password, first_name, last_name, email, status, created, updated)
VALUES ('admin', '$2y$12$Qvqbg1V3dd/1CzddlaDzKeDsv2h8RjiloxLPXjD9M5KvtCVs0OTNK', 'admin', 'admin',
        'admin@test.com', 'ACTIVE', CURRENT_DATE, CURRENT_DATE);

INSERT INTO users_roles (user_id, role_id)
VALUES ((SELECT user_id FROM users WHERE username = 'admin'),
           (SELECT role_id FROM roles WHERE name = 'ROLE_ADMIN'));