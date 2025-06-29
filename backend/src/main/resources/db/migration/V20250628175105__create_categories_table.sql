CREATE TABLE categories
(
    id         uuid         NOT NULL PRIMARY KEY,
    created_at timestamp(6),
    created_by varchar(255),
    updated_at timestamp(6),
    updated_by varchar(255),
    name       varchar(255) NOT NULL,
    type       varchar(255) CHECK ((type)::text = ANY ((ARRAY ['EXPENSE'::character varying, 'INCOME'::character varying])::text[])),
    id_user_id uuid REFERENCES users (id)
);

ALTER TABLE categories OWNER TO admin;
