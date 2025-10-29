DROP ALL OBJECTS;

CREATE TABLE institution_type
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE institution
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(200) NOT NULL,
    type_id          INT          NOT NULL REFERENCES institution_type (id),
    school_parent_id INT REFERENCES institution (id)
);

CREATE TABLE application
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(200) NOT NULL,
    app_key        VARCHAR(200) NOT NULL,
    url            VARCHAR(200) NOT NULL,
    institution_id INT          NOT NULL REFERENCES institution (id)
);

CREATE TABLE users
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(200) NOT NULL,
    school_id INT          NOT NULL REFERENCES institution (id)
);

create index institution_type_name_idx on institution_type (name);

create index institution_type_id_idx on institution (type_id);

create index users_name_idx on users (name);

create index application_app_key_idx on application (app_key);

create index application_institution_id_idx on application (institution_id);
