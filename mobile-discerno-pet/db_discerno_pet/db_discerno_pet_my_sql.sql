DROP DATABASE IF EXISTS db_discerno_pet;
CREATE DATABASE IF NOT EXISTS db_discerno_pet;
USE db_discerno_pet;


CREATE TABLE IF NOT EXISTS tb_usuario
(
    id                BIGINT UNSIGNED AUTO_INCREMENT,
    email             VARCHAR(100),
    telefone          VARCHAR(20),
    senha             VARCHAR(100) NOT NULL,
    tipo_autenticacao ENUM (
        'email',
        'telefone',
        'google'
        )                          NOT NULL,

    UNIQUE (email),
    UNIQUE (telefone),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_usuario_detalhes
(
    id                BIGINT UNSIGNED AUTO_INCREMENT,
    usuario_id        BIGINT UNSIGNED NOT NULL,
    nome              VARCHAR(100)    NOT NULL,
    email_recuperacao VARCHAR(100),
    cpf               VARCHAR(20),
    foto              VARCHAR(5000),
    nascimento        DATE,

    UNIQUE (cpf),
    UNIQUE (usuario_id),

    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id)
);

CREATE TABLE IF NOT EXISTS tb_endereco
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    usuario_id  BIGINT UNSIGNED NOT NULL,
    cep         VARCHAR(20)     NOT NULL,
    logradouro  VARCHAR(200)    NOT NULL,
    bairro      VARCHAR(100)    NOT NULL,
    cidade      VARCHAR(100)    NOT NULL,
    estado      VARCHAR(100)    NOT NULL,
    numero      VARCHAR(10),
    complemento VARCHAR(50),

    UNIQUE (usuario_id),

    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id)
);

CREATE TABLE IF NOT EXISTS tb_raca
(
    id   BIGINT UNSIGNED AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,

    UNIQUE (nome),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_pet_treinamento_ia
(
    id      BIGINT UNSIGNED AUTO_INCREMENT,
    nome    VARCHAR(100),
    id_raca BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_raca) REFERENCES tb_raca (id)
);

CREATE TABLE IF NOT EXISTS tb_fotos_treinamento
(
    id                    BIGINT UNSIGNED AUTO_INCREMENT,
    id_pet_treinamento_ia BIGINT UNSIGNED NOT NULL,
    url_foto              VARCHAR(5000)   NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_pet_treinamento_ia) REFERENCES tb_pet_treinamento_ia (id)
);

CREATE TABLE IF NOT EXISTS tb_pet
(
    id                    BIGINT UNSIGNED AUTO_INCREMENT,
    nome                  VARCHAR(100)    NOT NULL,
    foto                  VARCHAR(5000)   NOT NULL,
    idade                 DATE,
    usuario_id            BIGINT UNSIGNED NOT NULL,
    pet_treinamento_ia_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id),
    FOREIGN KEY (pet_treinamento_ia_id) REFERENCES tb_pet_treinamento_ia (id)
);

-- Usuário optando por autenticação por email
INSERT INTO tb_usuario (email, telefone, senha, tipo_autenticacao)
VALUES ('joao.silva@email.com', '+5511999999999', 'senha123', 'email');

-- Usuário optando por autenticação por Google
INSERT INTO tb_usuario (email, senha, tipo_autenticacao)
VALUES ('maria.souza@gmail.com', 'hashGoogle', 'google');

-- Usuário optando por autenticação por telefone
INSERT INTO tb_usuario (telefone, senha, tipo_autenticacao)
VALUES ('+19 974133884', 'senha456', 'telefone');

-- Detalhes do João
INSERT INTO tb_usuario_detalhes (usuario_id, nome, email_recuperacao, cpf, foto, nascimento)
VALUES (1, 'João Silva', 'joao.silva.recuperacao@email.com', '123.456.789-00', 'caminho/para/foto/joao.jpg',
        '1990-01-15');

-- Detalhes da Maria
INSERT INTO tb_usuario_detalhes (usuario_id, nome, cpf, nascimento)
VALUES (2, 'Maria Souza', '987.654.321-11', '1985-05-22'),
       (3, 'José Santos', '456.789.123-22', '1970-12-10');

INSERT INTO tb_endereco (usuario_id, cep, logradouro, complemento, bairro, cidade, estado)
VALUES (1, '01234-567', 'Rua das Flores', 'Apt 101', 'Jardim Primavera', 'São Paulo', 'SP'),
       (2, '98765-432', 'Avenida dos Sonhos', 'Centro', '', 'Campinas', 'SP'),
       (3, '65432-109', 'Rua das Palmeiras', 'Jardim Tropical', '', 'Santos', 'SP');

INSERT INTO tb_raca (nome)
VALUES ('Golden Retriever'),
       ('Labrador Retriever'),
       ('Poodle'),
       ('Pastor Alemão');

INSERT INTO tb_pet_treinamento_ia (id_raca)
VALUES (1),
       (2),
       (3),
       (4),
       (1);

-- João cadastrando seu Golden Retriever
INSERT INTO tb_pet (nome, foto, idade, usuario_id, pet_treinamento_ia_id)
VALUES ('Thor', 'caminho/para/foto/thor.jpg', '2020-03-10', 1, 1),
       ('Luna', 'caminho/para/foto/luna.jpg', '2019-01-20', 2, 2),
       ('Rex', 'caminho/para/foto/rex.jpg', '2018-12-01', 3, 1),
       ('Bella', 'caminho/para/foto/bella.jpg', '2017-09-15', 2, 1),
       ('Max', 'caminho/para/foto/max.jpg', '2016-07-30', 1, 2);

SELECT * FROM tb_usuario;
SELECT * FROM tb_usuario_detalhes;
SELECT * FROM tb_endereco;
SELECT * FROM tb_raca;
SELECT * FROM tb_pet_treinamento_ia;
SELECT * FROM tb_pet;
