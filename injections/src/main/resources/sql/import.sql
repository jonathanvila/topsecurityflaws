DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    id INT,
    username VARCHAR(255) ,
    password VARCHAR(255)
);
INSERT INTO usuario (id, username, password) VALUES (1, 'john', 'doe');
INSERT INTO usuario (id, username, password) VALUES (2, 'admin', 'admin');