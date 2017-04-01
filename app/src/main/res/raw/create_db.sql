CREATE TABLE `user`(
    id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE category(
    id integer NOT NULL PRIMARY KEY,
    name varchar(100) NOT NULL
);

CREATE TABLE game(
    id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    name varchar(255) NOT NULL,
    category_id integer NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id)
);