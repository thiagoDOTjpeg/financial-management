CREATE TABLE invoices_installments
(
    invoice_id     uuid NOT NULL REFERENCES invoices (id),
    installment_id uuid NOT NULL REFERENCES installments (id),
    PRIMARY KEY (invoice_id, installment_id)
);

ALTER TABLE invoices_installments OWNER TO admin;
