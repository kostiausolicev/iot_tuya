create table devices
(
    id              serial,
    category        varchar(255),
    name            varchar(255),
    tuya_id         varchar(255),

    primary key (id)
);