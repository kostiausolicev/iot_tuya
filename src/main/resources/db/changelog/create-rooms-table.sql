create table rooms (
    id                      bigserial primary key not null,
    name                    text not null,
    home_id                 bigint not null
);