create table installments
(
    id                 uuid             not null
        primary key,
    created_at         timestamp(6),
    created_by         varchar(255),
    updated_at         timestamp(6),
    updated_by         varchar(255),
    due_date           timestamp(6),
    installment_value  double precision not null,
    number_installment integer          not null
);

alter table installments
    owner to admin;

create table roles
(
    id          uuid not null
        primary key,
    description varchar(255)
);

alter table roles
    owner to admin;

create table users
(
    id                      uuid         not null
        primary key,
    created_at              timestamp(6),
    created_by              varchar(255),
    updated_at              timestamp(6),
    updated_by              varchar(255),
    account_non_expired     boolean      not null,
    account_non_locked      boolean      not null,
    account_status          varchar(255) not null
        constraint users_account_status_check
            check ((account_status)::text = ANY
        ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying])::text[])),
    credentials_non_expired boolean      not null,
    value                   varchar(255),
    full_name               varchar(255)
        constraint ukin1xa1v3pcuete8f8lxa47btl
            unique,
    last_login              timestamp(6),
    password                varchar(255) not null,
    username                varchar(255) not null
        constraint ukr43af9ap4edm43mmtq01oddj6
            unique
);

alter table users
    owner to admin;

create table bank_account
(
    id         uuid not null
        primary key,
    created_at timestamp(6),
    created_by varchar(255),
    updated_at timestamp(6),
    updated_by varchar(255),
    balance    double precision,
    bank_name  varchar(255),
    id_user    uuid
        constraint fkfbm0dfx7wd57lmys2yb8wubu1
            references users
);

alter table bank_account
    owner to admin;

create table cards
(
    id              uuid         not null
        primary key,
    created_at      timestamp(6),
    created_by      varchar(255),
    updated_at      timestamp(6),
    updated_by      varchar(255),
    card_brand      varchar(255) not null,
    closing_day     integer      not null,
    credit_limit    integer      not null,
    due_day         integer      not null,
    id_bank_account uuid
        constraint fkji04pt5gkqvw4mkgj6b8xab7c
            references bank_account
);

alter table cards
    owner to admin;

create table categories
(
    id         uuid         not null
        primary key,
    created_at timestamp(6),
    created_by varchar(255),
    updated_at timestamp(6),
    updated_by varchar(255),
    name       varchar(255) not null,
    type       varchar(255)
        constraint categories_type_check
            check ((type)::text = ANY ((ARRAY ['EXPENSE'::character varying, 'INCOME'::character varying])::text[])),
    id_user_id uuid
        constraint fk6vba0phbnbkh0s9c8t0kr36my
            references users
);

alter table categories
    owner to admin;

create table invoices
(
    id            uuid             not null
        primary key,
    created_at    timestamp(6),
    created_by    varchar(255),
    updated_at    timestamp(6),
    updated_by    varchar(255),
    billing_month date             not null
        constraint ukiehkncu77oq3k1x2m9hk3c8qr
            unique,
    closing_date  date             not null,
    due_date      date             not null,
    status        varchar(255)
        constraint invoices_status_check
            check ((status)::text = ANY
        ((ARRAY ['OPEN'::character varying, 'CLOSED'::character varying, 'PAID'::character varying])::text[])),
    total_value   double precision not null,
    id_card       uuid
        constraint fkbikm8xvig1pyaj8c3v903p7w
            references cards
);

alter table invoices
    owner to admin;

create table invoices_installments
(
    invoice_id     uuid not null
        constraint fkhujhkpawwkhbduh91rww65okg
            references invoices,
    installment_id uuid not null
        constraint fkl7rdth06l9kny1xt6femvf495
            references installments,
    primary key (invoice_id, installment_id)
);

alter table invoices_installments
    owner to admin;

create table transactions
(
    id             uuid             not null
        primary key,
    created_at     timestamp(6),
    created_by     varchar(255),
    updated_at     timestamp(6),
    updated_by     varchar(255),
    payment_type   varchar(255)     not null
        constraint transactions_payment_type_check
            check ((payment_type)::text = ANY
        ((ARRAY ['CREDIT'::character varying, 'DEBIT'::character varying, 'TRANSFER'::character varying])::text[])),
    timestamp      timestamp(6)     not null,
    value          double precision not null,
    id_category    uuid
        constraint fk59c06vdshsj3u2teciqt9fg55
            references categories,
    id_installment uuid
        constraint fklm2u6hq0xmenoullwb50yrvci
            references installments,
    id_invoice     uuid
        constraint fkda34o0iciwsm1uhwg0cgv577f
            references invoices
);

alter table transactions
    owner to admin;

create table installments_transactions
(
    installment_id  uuid not null
        constraint fkd1fw0yd6w24e30390to6eik3p
            references installments,
    transactions_id uuid not null
        constraint ukmrko6ft62h2v60q1uxhnrbq5f
            unique
        constraint fkk62nwa97w1488varua7qxq474
            references transactions
);

alter table installments_transactions
    owner to admin;

create table user_permissions
(
    id_user uuid not null
        constraint fk1lpagg77vo4fhuk3vlsqckbpd
            references users,
    id_role uuid not null
        constraint fkomn8wsdl2qx0wbblk0hlaudjr
            references roles
);

alter table user_permissions
    owner to admin;

