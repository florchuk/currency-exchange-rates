INSERT INTO exchangers
    (id, name, name_uk, created_at, updated_at)
VALUES
    (NEXTVAL('exchangers_sequence'), 'National Bank of Ukraine', 'Національний банк України', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXTVAL('exchangers_sequence'), 'PrivatBank (at branches)', 'ПриватБанк (у відділеннях)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXTVAL('exchangers_sequence'), 'UKRSIBBANK (at branches)', 'УКРСИББАНК (у відділеннях)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXTVAL('exchangers_sequence'), 'UKRSIBBANK (online)', 'УКРСИББАНК (онлайн)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO currencies
    (alphabetic_code, decimal_place, name, name_uk, created_at, updated_at)
VALUES
    ('UAH', 2, 'Hryvnia', 'Гривня', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('USD', 2, 'US Dollar', 'Долар США', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('EUR', 2, 'Euro', 'Євро', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

INSERT INTO authorities
    (id, name, created_at, updated_at)
VALUES
    (NEXTVAL('authorities_sequence'), 'PROVIDER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;