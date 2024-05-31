DROP DATABASE IF EXISTS db_discerno_pet;
CREATE DATABASE IF NOT EXISTS db_discerno_pet;
USE db_discerno_pet;


CREATE TABLE IF NOT EXISTS tb_usuario
(
    id                BIGINT UNSIGNED AUTO_INCREMENT,
    email             VARCHAR(100),
    telefone          VARCHAR(20),
    senha             VARCHAR(100)                         NOT NULL,
    tipo_autenticacao ENUM ('email', 'telefone', 'google') NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_usuario_detalhes
(
    id                BIGINT UNSIGNED AUTO_INCREMENT,
    usuario_id        BIGINT UNSIGNED UNIQUE NOT NULL,
    nome              VARCHAR(100)           NOT NULL,
    email_recuperacao VARCHAR(100),
    cpf               VARCHAR(20) UNIQUE,
    foto              VARCHAR(5000),
    nascimento        DATE,

    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id)
);

CREATE TABLE IF NOT EXISTS tb_endereco
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    usuario_id  BIGINT UNSIGNED UNIQUE NOT NULL,
    cep         VARCHAR(20)            NOT NULL,
    logradouro  VARCHAR(200)           NOT NULL,
    complemento VARCHAR(50),
    bairro      VARCHAR(100)           NOT NULL,
    cidade      VARCHAR(100)           NOT NULL,
    estado      VARCHAR(100)           NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id)
);

CREATE TABLE IF NOT EXISTS tb_raca
(
    id   BIGINT UNSIGNED AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_pet_treinamento_ia
(
    id      BIGINT UNSIGNED AUTO_INCREMENT,
    nome    VARCHAR(100)    NOT NULL,
    foto    VARCHAR(5000)   NOT NULL,
    id_raca BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_raca) REFERENCES tb_raca (id)
);

CREATE TABLE IF NOT EXISTS tb_pet
(
    id                    BIGINT UNSIGNED AUTO_INCREMENT,
    nome                  VARCHAR(100)    NOT NULL,
    raca                  VARCHAR(100)    NOT NULL,
    foto                  VARCHAR(5000),
    idade                 DATE,
    usuario_id            BIGINT UNSIGNED NOT NULL,
    pet_treinamento_ia_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id),
    FOREIGN KEY (pet_treinamento_ia_id) REFERENCES tb_pet_treinamento_ia (id)
);
