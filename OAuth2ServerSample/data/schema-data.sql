
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = FALSE;
SET client_min_messages = warning;
SET row_security = off;

SET TIME ZONE 'UTC';

/*	SCHEMA	*/

DROP SCHEMA IF EXISTS samples CASCADE;
CREATE SCHEMA IF NOT EXISTS samples;
ALTER SCHEMA samples OWNER TO postgres;
CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

SET search_path = samples, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = FALSE;


/*	TABLES	*/

CREATE TABLE samples.users(
  user_id VARCHAR(42) PRIMARY KEY,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  
  email VARCHAR(90) NOT NULL UNIQUE,
  username VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(90) NOT NULL,
  last_name VARCHAR(90) DEFAULT '',
  password VARCHAR(60) NOT NULL,

  createDateTime TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (current_timestamp),
  updateDateTime TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (current_timestamp)
);
ALTER TABLE samples.users OWNER TO postgres;


CREATE TABLE samples.roles(
  role_id INTEGER PRIMARY KEY,
role VARCHAR(90) NOT NULL
);
ALTER TABLE samples.roles OWNER TO postgres;

CREATE TABLE samples.user_role(
  user_id VARCHAR(42) NOT NULL REFERENCES  samples.users(user_id),
  role_id INTEGER NOT NULL REFERENCES  samples.roles(role_id)
);
ALTER TABLE samples.user_role OWNER TO postgres;



/*	BASE ROWS	*/

INSERT INTO samples.roles VALUES (1, 'ROLE_ADMIN');
INSERT INTO samples.roles VALUES (2, 'ROLE_USER');

INSERT INTO samples.users
  (user_id, active, email, username, name, last_name, password)
  VALUES ('01e3d8d5-1119-4111-b3d0-be6562ca5913', true, 'admin@admin.com',
          'admin', 'Some User', 'Admin Admin',
          '$2a$10$oeydcY6qusmu1/1kBoxZ4epbv5H3OY/uYgfFsT/OtFQDSt3kAdyeW' -- https://www.browserling.com/tools/bcrypt
            );

INSERT INTO samples.user_role VALUES ('01e3d8d5-1119-4111-b3d0-be6562ca5913', 1);
INSERT INTO samples.user_role VALUES ('01e3d8d5-1119-4111-b3d0-be6562ca5913', 2);
