CREATE TABLE installments
(
    id                 uuid             NOT NULL PRIMARY KEY,
    created_at         timestamp(6),
    created_by         varchar(255),
    updated_at         timestamp(6),
    updated_by         varchar(255),
    due_date           timestamp(6),
    installment_value  double precision NOT NULL,
    number_installment integer          NOT NULL
);

ALTER TABLE installments OWNER TO admin;
