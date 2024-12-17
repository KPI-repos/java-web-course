CREATE TABLE category
(
    id   UUID NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE cosmo_cat
(
    id   UUID NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_cosmocat PRIMARY KEY (id)
);

CREATE TABLE order_entry
(
    order_entry_id UUID    NOT NULL,
    product_id     UUID,
    quantity       INTEGER NOT NULL,
    CONSTRAINT pk_orderentry PRIMARY KEY (order_entry_id)
);

CREATE TABLE orders
(
    id    UUID  NOT NULL,
    price FLOAT NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE orders_entries
(
    order_id               UUID NOT NULL,
    entries_order_entry_id UUID NOT NULL
);

CREATE TABLE product
(
    id          UUID  NOT NULL,
    category_id UUID,
    name        VARCHAR(255),
    description VARCHAR(255),
    origin      VARCHAR(255),
    price       FLOAT NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE orders_entries
    ADD CONSTRAINT uc_orders_entries_entries_orderentryid UNIQUE (entries_order_entry_id);

ALTER TABLE order_entry
    ADD CONSTRAINT FK_ORDERENTRY_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE orders_entries
    ADD CONSTRAINT fk_ordent_on_order FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE orders_entries
    ADD CONSTRAINT fk_ordent_on_order_entry FOREIGN KEY (entries_order_entry_id) REFERENCES order_entry (order_entry_id);