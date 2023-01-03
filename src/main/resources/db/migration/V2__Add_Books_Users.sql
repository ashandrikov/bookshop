INSERT INTO books (id, author, title)
    VALUES (1, 'J.K.Rowling', 'Harry Potter 1'),
           (2, 'J.K.Rowling', 'Harry Potter 2'),
           (3, 'J.K.Rowling', 'Harry Potter 3');

INSERT INTO users (id, login, password, role, account_non_locked)
    VALUES (1, 'admin', '$2a$12$abz4SQpigWbke4.75IuXHOAbPEtiH5wSXaFSh0Si0kwzAkGqjrhbu', 'ADMINISTRATOR', true),
           (2, 'user', '$2a$12$Iu.a/M2Go5VfvpehrL656ueDyoaJbEVHz2e86NQ8wdinru3Co8wvi', 'USER', true),
           (3, 'editor', '$2a$12$OhTQp2zYC2KjkRVDbeJ/se2TYKPU1120kuQnQAKjcKIFTDpCQAlIi', 'EDITOR', true);

