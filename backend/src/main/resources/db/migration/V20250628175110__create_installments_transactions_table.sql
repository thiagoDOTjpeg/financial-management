CREATE TABLE installments_transactions
(
    installment_id  uuid NOT NULL REFERENCES installments (id),
    transactions_id uuid NOT NULL UNIQUE REFERENCES transactions (id),
    PRIMARY KEY (installment_id, transactions_id)
);

ALTER TABLE installments_transactions OWNER TO admin;
