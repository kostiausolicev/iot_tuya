create table if not exists users
(
    id       serial      not null,
    name     varchar(50) not null,
    username varchar(50) not null,
    password text        not null,
    primary key (id)
);

create table if not exists tokens
(
    token   uuid    not null,
    user_id integer not null,
    expired bigint,
    primary key (token)
);