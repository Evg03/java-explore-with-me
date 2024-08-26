DROP TABLE IF EXISTS hits;

CREATE TABLE IF NOT EXISTS hits (
id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
app VARCHAR,
uri VARCHAR,
ip VARCHAR,
created timestamp
);