drop TABLE IF EXISTS metric;

create table IF NOT EXISTS  metric
(
    id          bigserial primary key,
    sensor_id   bigint    not null,
    timestamp   timestamp not null,
    measurement float     not null,
    type        varchar   not null
);