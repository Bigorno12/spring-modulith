CREATE TABLE orders
(
    id                 UUID             NOT NULL,
    order_number       VARCHAR(25)      NOT NULL,
    product_code       VARCHAR(10)      NOT NULL,
    product_name       VARCHAR(100)     NOT NULL,
    product_price      DOUBLE PRECISION NOT NULL,
    quantity           INTEGER          NOT NULL,
    customer_name      VARCHAR(100)     NOT NULL,
    customer_email     VARCHAR(50)      NOT NULL,
    customer_phone     VARCHAR(50)      NOT NULL,
    delivery_address   VARCHAR(100)     NOT NULL,
    comments           VARCHAR(500),
    is_archived        CHAR(1)          NOT NULL DEFAULT 'N',
    status             VARCHAR(50)      NOT NULL,
    created_date       TIMESTAMP(6)     NOT NULL,
    last_modified_date TIMESTAMP(6)     NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uk_order_number UNIQUE (order_number),
    CONSTRAINT check_is_archived CHECK ( is_archived IN ('N', 'Y'))
);