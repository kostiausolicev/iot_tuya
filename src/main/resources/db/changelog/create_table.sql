create table if not exists users (
    id          serial not null,
    name        varchar(50) not null,
    username    varchar(50) not null,
    password    text not null,
    primary key (id)
);

create table if not exists tokens (
    token       uuid not null,
    user_id     integer references users(id) not null,
    primary key (token)
);