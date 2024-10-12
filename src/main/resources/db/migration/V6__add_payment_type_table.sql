CREATE TABLE payment_types (
    id BIGSERIAL NOT NULL,
    type character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE purchases
ADD COLUMN payment_types_id BIGINT,
ADD CONSTRAINT fk_payment_types
FOREIGN KEY (payment_types_id)
REFERENCES payment_types(id);