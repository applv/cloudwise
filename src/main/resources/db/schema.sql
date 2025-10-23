DROP ALL OBJECTS;

CREATE TABLE institution_type
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(200) NOT NULL UNIQUE,
    priority INT          NOT NULL UNIQUE
);

CREATE TABLE institution
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(200) NOT NULL UNIQUE,
    type_id INT          NOT NULL REFERENCES institution_type (id)
);

CREATE TABLE application
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(200) NOT NULL,
    app_id         VARCHAR(200) NOT NULL,
    url            VARCHAR(200) NOT NULL,
    institution_id INT REFERENCES institution (id)
);

CREATE TABLE users
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(200) NOT NULL UNIQUE,
    school_id INT          NOT NULL REFERENCES institution (id)
);
