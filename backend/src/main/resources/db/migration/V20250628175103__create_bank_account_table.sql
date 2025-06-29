CREATE TABLE bank_account
(
    id         uuid NOT NULL PRIMARY KEY,
    created_at timestamp(6),
    created_by varchar(255),
    updated_at timestamp(6),
    updated_by varchar(255),
    balance    double precision,
    bank_name  varchar(255),
    id_user    uuid REFERENCES users (id)
);

ALTER TABLE bank_account OWNER TO admin;
