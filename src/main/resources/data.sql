DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id UUID default random_uuid() PRIMARY KEY,
    login VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL
);

INSERT INTO users (login, email) VALUES
    ('adam', 'adam@email.com'),
    ('kasia', 'kasia@email.com'),
    ('bartek', 'bartek@email.com');

DROP TABLE IF EXISTS lectures;

CREATE TABLE lectures (
    id UUID default random_uuid() PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    thematicPath VARCHAR(50) NOT NULL,
    hStart VARCHAR(50) NOT NULL,
    hEnd VARCHAR(50) NOT NULL
);

INSERT INTO lectures (name, thematicPath, hStart, hEnd) VALUES
    ('prelekcja 1 sciezka 1', 'sciezka 1', '10:00', '11:45'),
    ('prelekcja 2 sciezka 1', 'sciezka 1', '12:00', '13:45'),
    ('prelekcja 3 sciezka 1', 'sciezka 1', '14:00', '15:45'),
    ('prelekcja 1 sciezka 2', 'sciezka 2', '10:00', '11:45'),
    ('prelekcja 2 sciezka 2', 'sciezka 2', '12:00', '13:45'),
    ('prelekcja 3 sciezka 2', 'sciezka 2', '14:00', '15:45'),
    ('prelekcja 1 sciezka 3', 'sciezka 3', '10:00', '11:45'),
    ('prelekcja 2 sciezka 3', 'sciezka 3', '12:00', '13:45'),
    ('prelekcja 3 sciezka 3', 'sciezka 3', '14:00', '15:45');

