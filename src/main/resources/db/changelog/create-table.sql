create table homes (
    id                  serial not null,
    name                varchar(50) not null,
    address             text,
    primary key (id)
);

create table rooms (
    id                  serial not null,
    name                varchar(50) not null,
    home_id             integer references homes(id),
    primary key (id)
);