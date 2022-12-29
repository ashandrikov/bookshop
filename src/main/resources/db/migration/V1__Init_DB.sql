create table if not exists users (
    id bigint not null auto_increment,
    login varchar(255) unique,
    password varchar(255),
    role varchar(255),
    account_non_locked bit,
    primary key (id)
);

insert into users (id, login, password, role)
    values (1, 'admin', 'admin', 'ADMINISTRATOR');
insert into users (id, login, password, role)
    values (2, 'user', 'user', 'USER');
insert into users (id, login, password, role)
    values (3, 'editor', 'editor', 'EDITOR');

create table books (
    id bigint not null auto_increment,
    author varchar(25) not null,
    title varchar(25) not null,
    primary key (id)
);

insert into books (id, author, title)
    VALUES (1, 'J.K.Rowling', 'Harry Potter 1');