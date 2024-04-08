create table devices
(
    id              serial,
    category        varchar(255),
    name            varchar(255),
    home_id         integer,
    room_id         integer,
    tuya_id         varchar(255),

    primary key (id)
);