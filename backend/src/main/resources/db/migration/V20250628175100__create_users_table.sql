CREATE TABLE users
(
    id                      uuid         NOT NULL PRIMARY KEY,
    created_at              timestamp(6),
    created_by              varchar(255),
    updated_at              timestamp(6),
    updated_by              varchar(255),
    account_non_expired     boolean      NOT NULL,
    account_non_locked      boolean      NOT NULL,
    account_status          varchar(255) NOT NULL CHECK ((account_status)::text = ANY ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying])::text[])),
    credentials_non_expired boolean      NOT NULL,
    value                   varchar(255),
    full_name               varchar(255) CONSTRAINT ukin1xa1v3pcuete8f8lxa47btl UNIQUE,
    last_login              timestamp(6),
    password                varchar(255) NOT NULL,
    username                varchar(255) NOT NULL CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE
);

ALTER TABLE users OWNER TO admin;
