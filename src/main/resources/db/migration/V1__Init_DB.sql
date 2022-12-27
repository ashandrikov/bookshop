create table books (
    id bigint not null auto_increment,
    author varchar(25) not null,
    title varchar(25) not null,
    primary key (id)
);

insert into books (id, author, title)
    VALUES (1, 'J.K.Rowling', 'Harry Potter 1');