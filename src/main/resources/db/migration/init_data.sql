CREATE TABLE accounts
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id                  VARCHAR(255) NOT NULL,
    name                     VARCHAR(255) NOT NULL,
    acnt_type                VARCHAR(50),
    password                 VARCHAR(255) NOT NULL,
    email                    VARCHAR(255) NOT NULL,
    last_login_at            DATETIME,
    created_at               DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at               DATETIME ON UPDATE CURRENT_TIMESTAMP,
    deleted_at               DATETIME,
    last_password_updated_at DATETIME,
    trial_cnt                INT      DEFAULT 0
);

INSERT INTO accounts (user_id, name, acnt_type, password, email, last_login_at, last_password_updated_at, trial_cnt)
VALUES ('user123', 'John Doe', 'USER', 'password123', 'john.doe@example.com', '2024-06-01 10:30:00',
        '2024-05-01 09:00:00', 1);

INSERT INTO accounts (user_id, name, acnt_type, password, email, last_login_at, last_password_updated_at, trial_cnt)
VALUES ('user456', 'Jane Smith', 'MANAGER', 'password456', 'jane.smith@example.com', '2024-06-10 14:45:00',
        '2024-05-15 11:15:00', 2);

CREATE TABLE roles
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME ON UPDATE CURRENT_TIMESTAMP
);


INSERT INTO roles (name, description, created_at, updated_at)
VALUES ('ADMIN', 'admin', now(), now());

INSERT INTO roles (name, description, created_at, updated_at)
VALUES ('PLAIN', 'plain', now(), now());

INSERT INTO roles (name, description, created_at, updated_at)
VALUES ('AAA', 'aaa', now(), now());

INSERT INTO roles (name, description)
VALUES ('BBB', 'bbb');

CREATE TABLE accounts_roles
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    role_id    BIGINT NOT NULL,
    UNIQUE KEY unique_account_role (account_id, role_id),
    FOREIGN KEY (account_id) REFERENCES accounts (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO accounts_roles(account_id, role_id)
VALUES (1, 1);

INSERT INTO accounts_roles(account_id, role_id)
VALUES (1, 2);

INSERT INTO accounts_roles(account_id, role_id)
VALUES (2, 1);

INSERT INTO accounts_roles(account_id, role_id)
VALUES (2, 3);

INSERT INTO accounts_roles(account_id, role_id)
VALUES (2, 4);
