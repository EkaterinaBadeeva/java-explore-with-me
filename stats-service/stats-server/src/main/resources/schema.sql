DROP TABLE IF EXISTS hits CASCADE;

-- записи
CREATE TABLE IF NOT EXISTS hits (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  app VARCHAR(255) NOT NULL,
  uri VARCHAR(512) NOT NULL,
  ip VARCHAR(15) NOT NULL,
  timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT pk_hit PRIMARY KEY (id)
);