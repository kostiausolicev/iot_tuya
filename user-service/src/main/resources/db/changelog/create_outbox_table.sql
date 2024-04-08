create table if not exists outbox
(
    id      serial      not null,
    topic   varchar(50) not null,
    message text
);