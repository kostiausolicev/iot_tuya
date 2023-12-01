create table if not exists tokens (
    token           uuid not null,
    user_id         integer not null,
    expired_time    bigint,
    primary key (token)
);