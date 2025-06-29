CREATE TABLE invoices
(
    id            uuid             NOT NULL PRIMARY KEY,
    created_at    timestamp(6),
    created_by    varchar(255),
    updated_at    timestamp(6),
    updated_by    varchar(255),
    billing_month date             NOT NULL CONSTRAINT ukiehkncu77oq3k1x2m9hk3c8qr UNIQUE,
    closing_date  date             NOT NULL,
    due_date      date             NOT NULL,
    status        varchar(255) CHECK ((status)::text = ANY ((ARRAY ['OPEN'::character varying, 'CLOSED'::character varying, 'PAID'::character varying])::text[])),
    total_value   double precision NOT NULL,
    id_card       uuid REFERENCES cards (id)
);

ALTER TABLE invoices OWNER TO admin;
