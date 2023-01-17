CREATE TABLE IF NOT EXISTS tb_user (
     id SERIAL,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL,
     CONSTRAINT tb_user_pk PRIMARY KEY (id),
     CONSTRAINT tb_user_uk UNIQUE (name, email)
);

CREATE TABLE IF NOT EXISTS tb_item (
    id SERIAL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT tb_item_pk PRIMARY KEY (id),
    CONSTRAINT tb_item_uk UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS tb_order (
    id SERIAL,
    creation_date TIMESTAMP NOT NULL,
    item_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    CONSTRAINT tb_order_pk PRIMARY KEY (id),
    CONSTRAINT tb_order_fk1 FOREIGN KEY (item_id) REFERENCES tb_item (id),
    CONSTRAINT tb_order_fk2 FOREIGN KEY (user_id) REFERENCES tb_user (id)
);

CREATE TABLE IF NOT EXISTS tb_stock_movement (
    id SERIAL,
    creation_date TIMESTAMP NOT NULL,
    item_id INTEGER NOT NULL,
    order_id INTEGER,
    quantity INTEGER NOT NULL,
    CONSTRAINT tb_stock_movement_pk PRIMARY KEY (id),
    CONSTRAINT tb_stock_movement_fk1 FOREIGN KEY (item_id) REFERENCES tb_item (id),
    CONSTRAINT tb_stock_movement_fk2 FOREIGN KEY (order_id) REFERENCES tb_order (id)
);