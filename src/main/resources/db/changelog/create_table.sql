create table if not exists users (
    id          serial not null,
    name        varchar(50) not null,
    username    varchar(50) not null,
    password    text not null,
    primary key (id)
);