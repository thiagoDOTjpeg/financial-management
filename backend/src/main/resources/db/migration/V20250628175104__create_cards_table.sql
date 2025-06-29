CREATE TABLE cards
(
    id              uuid         NOT NULL PRIMARY KEY,
    created_at      timestamp(6),
    created_by      varchar(255),
    updated_at      timestamp(6),
    updated_by      varchar(255),
    card_brand      varchar(255) NOT NULL,
    closing_day     integer      NOT NULL,
    credit_limit    integer      NOT NULL,
    due_day         integer      NOT NULL,
    id_bank_account uuid REFERENCES bank_account (id)
);

ALTER TABLE cards OWNER TO admin;
