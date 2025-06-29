CREATE TABLE user_permissions
(
    id_user uuid NOT NULL REFERENCES users (id),
    id_role uuid NOT NULL REFERENCES roles (id),
    PRIMARY KEY (id_user, id_role)
);

ALTER TABLE user_permissions OWNER TO admin;
