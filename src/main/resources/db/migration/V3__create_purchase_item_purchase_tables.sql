CREATE TABLE purchases (
    id BIGSERIAL NOT NULL,
    "date" TIMESTAMPTZ NOT NULL,
    totalValue REAL NOT NULL,
    supermarket_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_supermarket FOREIGN KEY (supermarket_id)
            REFERENCES supermarkets (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
            NOT VALID
);

ALTER TABLE IF EXISTS purchases
    OWNER to postgres;

CREATE TABLE items_purchase (
    id BIGSERIAL NOT NULL,
    value REAL NOT NULL,
    item_id BIGINT NOT NULL,
    purchase_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_items FOREIGN KEY (item_id)
            REFERENCES items (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
            NOT VALID,
    CONSTRAINT fk_purchases FOREIGN KEY (purchase_id)
            REFERENCES purchases (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
            NOT VALID
);

ALTER TABLE IF EXISTS items_purchase
    OWNER to postgres;



