DROP TABLE IF EXISTS metric;

CREATE TABLE IF NOT EXISTS metric
(
	id          bigserial PRIMARY KEY,
	sensor_id   bigint    NOT NULL,
	timestamp   timestamp NOT NULL,
	measurement float     NOT NULL,
	type        varchar   NOT NULL
);