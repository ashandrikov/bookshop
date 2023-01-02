DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS shoppingcarts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;

CREATE TABLE books (
    id          BIGINT AUTO_INCREMENT NOT NULL,
    author      varchar(25) NOT NULL,
    title       varchar(25) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE shoppingcarts (
    id          BIGINT AUTO_INCREMENT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id              BIGINT AUTO_INCREMENT NOT NULL,
    login           varchar(255) UNIQUE,
    password        varchar(255),
    role            varchar(255),
    shoppingcart_id bigint,
    account_non_locked bit,
    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_id     BIGINT NULL,
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

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_BOOK FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

# ALTER TABLE orders
#     ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_BASKET FOREIGN KEY (shoppingcart_id) REFERENCES shoppingcarts (id);