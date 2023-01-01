CREATE TABLE if not exists books (
    id          BIGINT AUTO_INCREMENT NOT NULL,
    author      varchar(25) NOT NULL,
    title       varchar(25) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE if not exists baskets (
    id          BIGINT AUTO_INCREMENT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE if not exists users (
    id          BIGINT AUTO_INCREMENT NOT NULL,
    login       varchar(255) UNIQUE,
    password    varchar(255),
    role        varchar(255),
    basket_id   bigint NOT NULL,
    account_non_locked bit,
    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_id     BIGINT NULL,
    PRIMARY KEY (id)
);

# ALTER TABLE orders
#     ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_BASKET FOREIGN KEY (basket_id) REFERENCES baskets (id);