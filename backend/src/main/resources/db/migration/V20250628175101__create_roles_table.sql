CREATE TABLE roles
(
    id          uuid NOT NULL PRIMARY KEY,
    description varchar(255)
);

ALTER TABLE roles OWNER TO admin;
