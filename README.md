create this table in h2-console
http://localhost:8080/h2-console

CREATE TABLE books
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    description VARCHAR(255),
    published NUMBER(1)
);