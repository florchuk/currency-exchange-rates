CREATE TABLE IF NOT EXISTS exchangers (
    id INTEGER,
    name CHARACTER VARYING(255) NOT NULL,
    name_uk CHARACTER VARYING(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS exchangers_name_unique_index ON exchangers (name);
CREATE UNIQUE INDEX IF NOT EXISTS exchangers_name_uk_unique_index ON exchangers (name_uk);

CREATE SEQUENCE IF NOT EXISTS exchangers_sequence AS INTEGER INCREMENT BY 1 MINVALUE 1 START WITH 1 OWNED BY exchangers.id;

COMMENT ON TABLE exchangers IS 'Table of exchangers.';
COMMENT ON INDEX exchangers_name_unique_index IS 'Enforces uniqueness on name column of the exchangers table (english).';
COMMENT ON INDEX exchangers_name_uk_unique_index IS 'Enforces uniqueness on name_uk column of the exchangers table (ukrainian).';
COMMENT ON SEQUENCE exchangers_sequence IS 'Used to generate primary keys of the exchangers table.';
COMMENT ON COLUMN exchangers.id IS 'Primary key. The unique record identifier.';
COMMENT ON COLUMN exchangers.name IS 'The exchanger name (english).';
COMMENT ON COLUMN exchangers.name_uk IS 'The exchanger name (ukrainian).';
COMMENT ON COLUMN exchangers.created_at IS 'The record creation timestamp.';
COMMENT ON COLUMN exchangers.updated_at IS 'The record updating timestamp.';

CREATE TABLE IF NOT EXISTS currencies (
    alphabetic_code CHARACTER(3) NOT NULL,
    decimal_place INTEGER NOT NULL,
    name CHARACTER VARYING(255) NOT NULL,
    name_uk CHARACTER VARYING(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (alphabetic_code)
);

COMMENT ON TABLE currencies IS 'Table of ISO 4217 currency codes.';
COMMENT ON COLUMN currencies.alphabetic_code IS 'Primary key. Unique ISO 4217 currency alphabetic code.';
COMMENT ON COLUMN currencies.decimal_place IS 'Number of currency decimal places.';
COMMENT ON COLUMN currencies.name IS 'The currency name (english).';
COMMENT ON COLUMN currencies.name_uk IS 'The currency name (ukrainian).';
COMMENT ON COLUMN currencies.created_at IS 'The record creation timestamp.';
COMMENT ON COLUMN currencies.updated_at IS 'The record updating timestamp.';

CREATE TABLE IF NOT EXISTS rates (
	id INTEGER,
	exchanger_id INTEGER NOT NULL,
	unit INTEGER NOT NULL,
	unit_currency_alphabetic_code CHARACTER(3) NOT NULL,
	rate_currency_alphabetic_code CHARACTER(3) NOT NULL,
	buy_rate DOUBLE PRECISION NOT NULL,
	sale_rate DOUBLE PRECISION NOT NULL,
	created_at TIMESTAMP(6) NOT NULL,
	updated_at TIMESTAMP(6) NOT NULL,
	PRIMARY KEY (id),
    FOREIGN KEY (exchanger_id) REFERENCES exchangers (id) ON DELETE CASCADE,
    FOREIGN KEY (unit_currency_alphabetic_code) REFERENCES currencies (alphabetic_code) ON DELETE CASCADE,
    FOREIGN KEY (rate_currency_alphabetic_code) REFERENCES currencies (alphabetic_code) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS rates_unique_index ON rates (
    exchanger_id,
    unit_currency_alphabetic_code,
    rate_currency_alphabetic_code
);

CREATE SEQUENCE IF NOT EXISTS rates_sequence AS INTEGER INCREMENT BY 1 MINVALUE 1 START WITH 1 OWNED BY rates.id;

COMMENT ON TABLE rates IS 'Table of currency exchange rates.';
COMMENT ON INDEX rates_unique_index IS 'Enforces uniqueness on exchanger_id, unit_currency_alphabetic_code and rate_currency_alphabetic_code columns of the rates table.';
COMMENT ON SEQUENCE rates_sequence IS 'Used to generate primary keys of the rates table.';
COMMENT ON COLUMN rates.id IS 'Primary key. The unique record identifier.';
COMMENT ON COLUMN rates.exchanger_id IS 'Foreign key referencing the id column of the exchangers table.';
COMMENT ON COLUMN rates.unit IS 'The unit of exchange is represented in the currency specified by unit_currency_alphabetic_code column.';
COMMENT ON COLUMN rates.unit_currency_alphabetic_code IS 'Foreign key referencing the alphabetic_code column of the currencies table.';
COMMENT ON COLUMN rates.rate_currency_alphabetic_code IS 'Foreign key referencing the alphabetic_code column of the currencies table.';
COMMENT ON COLUMN rates.buy_rate IS 'The buy rate is represented in the currency specified by rate_currency_alphabetic_code column.';
COMMENT ON COLUMN rates.sale_rate IS 'The sale rate is represented in the currency specified by rate_currency_alphabetic_code column.';
COMMENT ON COLUMN rates.created_at IS 'The record creation timestamp.';
COMMENT ON COLUMN rates.updated_at IS 'The record updating timestamp.';

CREATE TABLE IF NOT EXISTS archive_rates (
	id BIGINT,
	rate_id INTEGER NOT NULL,
	unit INTEGER NOT NULL,
	buy_rate DOUBLE PRECISION NOT NULL,
	sale_rate DOUBLE PRECISION NOT NULL,
	created_at TIMESTAMP(6) NOT NULL,
	updated_at TIMESTAMP(6) NOT NULL,
	PRIMARY KEY (id),
    FOREIGN KEY (rate_id) REFERENCES rates (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS archive_rates_unique_index ON archive_rates (rate_id, created_at);

CREATE SEQUENCE IF NOT EXISTS archive_rates_sequence AS BIGINT INCREMENT BY 1 MINVALUE 1 START WITH 1 OWNED BY archive_rates.id;

COMMENT ON TABLE archive_rates IS 'The archive table of currency exchange rates.';
COMMENT ON INDEX archive_rates_unique_index IS 'Enforces uniqueness on rate_id and created_at columns of the archive_rates table. Optimizes queries filtering by rate_id and ordering by created_at.';
COMMENT ON SEQUENCE archive_rates_sequence IS 'Used to generate primary keys of the archive_rates table.';
COMMENT ON COLUMN archive_rates.id IS 'Primary key. The unique record identifier.';
COMMENT ON COLUMN archive_rates.rate_id IS 'Foreign key referencing the id column of the rates table.';
COMMENT ON COLUMN archive_rates.unit IS 'The unit of exchange is represented in the currency specified by unit_currency_alphabetic_code column of rates table.';
COMMENT ON COLUMN archive_rates.buy_rate IS 'The buy rate is represented in the currency specified by rate_currency_alphabetic_code column of rates table.';
COMMENT ON COLUMN archive_rates.sale_rate IS 'The sale rate is represented in the currency specified by rate_currency_alphabetic_code column of rates table.';
COMMENT ON COLUMN archive_rates.created_at IS 'The record creation timestamp.';
COMMENT ON COLUMN archive_rates.updated_at IS 'The record updating timestamp.';

CREATE TABLE IF NOT EXISTS users (
    id INTEGER,
    username CHARACTER VARYING(255) NOT NULL,
    password CHARACTER VARYING(255) NOT NULL,
    is_account_non_expired BOOLEAN NOT NULL,
    is_account_non_locked BOOLEAN NOT NULL,
    is_credentials_non_expired BOOLEAN NOT NULL,
    is_enabled BOOLEAN NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS users_username_unique_index ON users (username);

CREATE SEQUENCE IF NOT EXISTS users_sequence AS INTEGER INCREMENT BY 1 MINVALUE 1 START WITH 1 OWNED BY users.id;

COMMENT ON TABLE users IS 'Table of users.';
COMMENT ON INDEX users_username_unique_index IS 'Enforces uniqueness on username column of the users table.';
COMMENT ON SEQUENCE users_sequence IS 'Used to generate primary keys of the users table.';
COMMENT ON COLUMN users.id IS 'Primary key. The unique record identifier.';
COMMENT ON COLUMN users.username IS 'The username.';
COMMENT ON COLUMN users.password IS 'The password.';
COMMENT ON COLUMN users.is_account_non_expired IS 'An indicator that determines whether the account is not expired.';
COMMENT ON COLUMN users.is_account_non_locked IS 'An indicator that determines whether the account is not locked.';
COMMENT ON COLUMN users.is_credentials_non_expired IS 'An indicator that determines whether the account credentials is not expired.';
COMMENT ON COLUMN users.is_enabled IS 'An indicator that determines whether the account is not disabled.';
COMMENT ON COLUMN users.created_at IS 'The record creation timestamp.';
COMMENT ON COLUMN users.updated_at IS 'The record updating timestamp.';

CREATE TABLE IF NOT EXISTS authorities (
    id INTEGER,
    name CHARACTER VARYING(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS authorities_name_unique_index ON authorities (name);

CREATE SEQUENCE IF NOT EXISTS authorities_sequence AS INTEGER INCREMENT BY 1 MINVALUE 1 START WITH 1 OWNED BY authorities.id;

COMMENT ON TABLE authorities IS 'Table of authorities.';
COMMENT ON INDEX authorities_name_unique_index IS 'Enforces uniqueness on name column of the authorities table.';
COMMENT ON SEQUENCE authorities_sequence IS 'Used to generate primary keys of the authorities table.';
COMMENT ON COLUMN authorities.id IS 'Primary key. The unique record identifier.';
COMMENT ON COLUMN authorities.name IS 'The authority name.';
COMMENT ON COLUMN authorities.created_at IS 'The record creation timestamp.';
COMMENT ON COLUMN authorities.updated_at IS 'The record updating timestamp.';

CREATE TABLE IF NOT EXISTS user_authorities (
    user_id INTEGER NOT NULL,
    authority_id INTEGER NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (authority_id) REFERENCES authorities (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS user_authorities_unique_index ON user_authorities (user_id, authority_id);

COMMENT ON TABLE user_authorities IS 'Table of user authorities.';
COMMENT ON INDEX user_authorities_unique_index IS 'Enforces uniqueness on user_id and authority_id columns of the user_authorities table.';
COMMENT ON COLUMN user_authorities.user_id IS 'Foreign key referencing the id column of the users table.';
COMMENT ON COLUMN user_authorities.authority_id IS 'Foreign key referencing the id column of the authorities table.';
COMMENT ON COLUMN user_authorities.created_at IS 'The record creation timestamp.';
COMMENT ON COLUMN user_authorities.updated_at IS 'The record updating timestamp.';