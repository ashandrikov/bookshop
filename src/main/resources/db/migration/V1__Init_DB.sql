DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_details;

CREATE TABLE books (
    id          BIGINT AUTO_INCREMENT NOT NULL,
    author      varchar(25)           NOT NULL,
    title       varchar(25)           NOT NULL,
    price       decimal(10, 2)        NOT NULL,
    year        INT                   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    login               VARCHAR(255),
    password            VARCHAR(255),
    role                VARCHAR(255),
    account_non_locked  BIT,
    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT                NULL,
    status     VARCHAR(255)          NULL,
    order_time DATETIME              NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart_items
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    user_id  BIGINT                NULL,
    book_id  BIGINT                NULL,
    quantity INT                   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE order_details
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    book_id  BIGINT                NULL,
    quantity INT                   NOT NULL,
    order_id BIGINT                NULL,
    PRIMARY KEY (id)
);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_BOOK FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAILS_ON_BOOK FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAILS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id)
