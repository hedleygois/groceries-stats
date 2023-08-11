CREATE TABLE franchises
(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS franchises
    OWNER to postgres;

CREATE TABLE supermarkets
(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    franchise_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_franchises FOREIGN KEY (franchise_id)
        REFERENCES franchises (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS supermarkets
    OWNER to postgres;

CREATE TABLE items_category
(
    id bigserial NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS items_category
    OWNER to postgres;

CREATE TABLE brands
(
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS brands
    OWNER to postgres;

CREATE TABLE items
(
    id bigserial NOT NULL,
    name character varying NOT NULL,
    category bigint NOT NULL,
    brand bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_category FOREIGN KEY (category)
        REFERENCES items_category (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_brand FOREIGN KEY (brand)
        REFERENCES brands (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS items
    OWNER to postgres;

