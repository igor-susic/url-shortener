CREATE TABLE ROLES (
    id IDENTITY NOT NULL,
    role VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE ACCOUNTS (
    account_id VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT account_to_role FOREIGN KEY(role_id) REFERENCES ROLES(id),
    PRIMARY KEY(account_id)
);

CREATE TABLE URLS (
    encoded_url VARCHAR(16),
    original_url TEXT NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    CONSTRAINT url_to_account FOREIGN KEY(account_id) REFERENCES ACCOUNTS(account_id),
    number_of_clicks INT NOT NULL DEFAULT 0,
    redirect_type INT NOT NULL DEFAULT 302,
    id IDENTITY NOT NULL
);

INSERT INTO ROLES (id, role) VALUES (DEFAULT, 'ROLE_USER')