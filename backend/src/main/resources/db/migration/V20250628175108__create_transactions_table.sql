CREATE TABLE transactions
(
    id             uuid             NOT NULL PRIMARY KEY,
    created_at     timestamp(6),
    created_by     varchar(255),
    updated_at     timestamp(6),
    updated_by     varchar(255),
    payment_type   varchar(255)     NOT NULL CHECK ((payment_type)::text = ANY ((ARRAY ['CREDIT'::character varying, 'DEBIT'::character varying, 'TRANSFER'::character varying])::text[])),
    timestamp      timestamp(6)     NOT NULL,
    value          double precision NOT NULL,
    id_category    uuid REFERENCES categories (id),
    id_installment uuid REFERENCES installments (id),
    id_invoice     uuid REFERENCES invoices (id)
);

ALTER TABLE transactions OWNER TO admin;
