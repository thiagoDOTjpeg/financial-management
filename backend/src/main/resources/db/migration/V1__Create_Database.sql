create table public.installments
(
    id                 uuid     not null
        primary key,
    created_at         timestamp(6),
    created_by         varchar(255),
    updated_at         timestamp(6),
    updated_by         varchar(255),
    due_date           timestamp(6),
    id_transaction     uuid,
    installment_value  double precision not null,
    number_installment integer          not null
);

create table public.roles
(
    id          uuid not null
        primary key,
    description varchar(255)
);

create table public.users
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

create table public.bank_account
(
    id         uuid not null
        primary key,
    created_at timestamp(6),
    created_by varchar(255),
    updated_at timestamp(6),
    updated_by varchar(255),
    balance    double precision,
    bank_name  varchar(255),
    id_user uuid
        constraint fk6e7hqh006av9jvj1u6h31d7u2
            references public.users
);

create table public.cards
(
    id              uuid not null
        primary key,
    created_at      timestamp(6),
    created_by      varchar(255),
    updated_at      timestamp(6),
    updated_by      varchar(255),
    card_brand      varchar(255) not null,
    credit_limit    integer      not null,
    id_bank_account uuid
        constraint fkji04pt5gkqvw4mkgj6b8xab7c
            references public.bank_account
);

create table public.categories
(
    id         uuid not null
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
            references public.users
);

create table public.invoices
(
    id              uuid     not null
        primary key,
    created_at      timestamp(6),
    created_by      varchar(255),
    updated_at      timestamp(6),
    updated_by      varchar(255),
    billing_month   date             not null
        constraint ukiehkncu77oq3k1x2m9hk3c8qr
            unique,
    due_date        date             not null,
    status          varchar(255)
        constraint invoices_status_check
            check ((status)::text = ANY ((ARRAY ['PENDING'::character varying, 'PAID'::character varying])::text[])),
    total_value     double precision not null,
    id_card         uuid
        constraint fkbikm8xvig1pyaj8c3v903p7w
            references public.cards,
    id_installments uuid
        constraint fklh9go7xyu0k43mgx3m83eac81
            references public.installments
);

create table public.installments_invoices
(
    installment_id uuid not null
        constraint fkf2raglcgtl85equ3hvivxwxi
            references public.installments,
    invoices_id    uuid not null
        constraint uk3s0yh3vcfhntjlgnos8ynm4nr
            unique
        constraint fk3ke2nfb7aenvhctar6t6fw8xh
            references public.invoices
);

create table public.transactions
(
    id           uuid     not null
        primary key,
    created_at   timestamp(6),
    created_by   varchar(255),
    updated_at   timestamp(6),
    updated_by   varchar(255),
    payment_type varchar(255)     not null
        constraint transactions_payment_type_check
            check ((payment_type)::text = ANY
        ((ARRAY ['CREDIT'::character varying, 'DEBIT'::character varying, 'TRANSFER'::character varying])::text[])),
    timestamp    timestamp(6)     not null,
    value        double precision not null,
    id_category  uuid
        constraint fk59c06vdshsj3u2teciqt9fg55
            references public.categories,
    id_invoice   uuid
        constraint fkda34o0iciwsm1uhwg0cgv577f
            references public.invoices
);

create table public.transactions_installment
(
    transaction_id uuid not null
        constraint fkn8fartr4lg161ioekhb74ltku
            references public.transactions,
    installment_id uuid not null
        constraint uk9gj6l560is0pv4j978oxaevgu
            unique
        constraint fk8r88i59yx9n8ohfu7nm2ecdrx
            references public.installments
);

create table public.user_permissions
(
    id_user uuid         not null
        constraint fk1lpagg77vo4fhuk3vlsqckbpd
            references public.users,
    id_role uuid not null
        constraint fkomn8wsdl2qx0wbblk0hlaudjr
            references public.roles
);
