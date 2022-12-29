create table if not exists users (
    id bigint not null auto_increment,
    login varchar(255) unique,
    password varchar(255),
    role varchar(255),
    account_non_locked bit,
    primary key (id)
);

insert into users (id, login, password, role, account_non_locked)
    values (1, 'admin', '$2a$12$abz4SQpigWbke4.75IuXHOAbPEtiH5wSXaFSh0Si0kwzAkGqjrhbu', 'ADMINISTRATOR', true);
insert into users (id, login, password, role, account_non_locked)
    values (2, 'user', '$2a$12$Iu.a/M2Go5VfvpehrL656ueDyoaJbEVHz2e86NQ8wdinru3Co8wvi', 'USER', true);
insert into users (id, login, password, role, account_non_locked)
    values (3, 'editor', '$2a$12$OhTQp2zYC2KjkRVDbeJ/se2TYKPU1120kuQnQAKjcKIFTDpCQAlIi', 'EDITOR', true);

create table books (
    id bigint not null auto_increment,
    author varchar(25) not null,
    title varchar(25) not null,
    primary key (id)
);

insert into books (id, author, title)
    VALUES (1, 'J.K.Rowling', 'Harry Potter 1');