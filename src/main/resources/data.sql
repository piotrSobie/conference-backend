DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id UUID default random_uuid() PRIMARY KEY,
    login VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL
);

INSERT INTO users (login, email) VALUES
    ('adam', 'adam@email.com'),
    ('kasia', 'kasia@email.com'),
    ('bartek', 'bartek@email.com')