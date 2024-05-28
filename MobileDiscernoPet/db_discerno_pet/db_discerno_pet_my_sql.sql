DROP DATABASE IF EXISTS db_discerno_pet;
CREATE DATABASE IF NOT EXISTS db_discerno_pet;
USE db_discerno_pet;


CREATE TABLE IF NOT EXISTS tb_usuario
(
    id                BIGINT UNSIGNED AUTO_INCREMENT,
    nome              VARCHAR(100) NOT NULL,
    email             VARCHAR(100) NOT NULL,
    email_recuperacao VARCHAR(100) NOT NULL,
    senha             VARCHAR(100) NOT NULL,
    telefone          VARCHAR(20),
    cep               VARCHAR(20),
    cpf               VARCHAR(20),
    foto              VARCHAR(5000),
    nascimento        DATE,

    UNIQUE (email),
    UNIQUE (cpf),

    PRIMARY KEY (id)
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

CREATE TABLE IF NOT EXISTS tb_cadastro_cnpj
(
    id                BIGINT UNSIGNED AUTO_INCREMENT,
    nome              VARCHAR(100) NOT NULL,
    cnpj              VARCHAR(20)  NOT NULL,
    email             VARCHAR(100) NOT NULL,
    email_recuperacao VARCHAR(100) NOT NULL,
    senha             VARCHAR(100) NOT NULL,
    foto              VARCHAR(5000),
    contato1          VARCHAR(20),
    contato2          VARCHAR(20),
    contato3          VARCHAR(20),

    UNIQUE (cnpj),
    UNIQUE (email),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_endereco
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    logradouro  VARCHAR(200) NOT NULL,
    numero      VARCHAR(10)  NOT NULL,
    complemento VARCHAR(50),
    bairro      VARCHAR(100) NOT NULL,
    cidade      VARCHAR(100) NOT NULL,
    estado      VARCHAR(100) NOT NULL,
    cep         VARCHAR(20)  NOT NULL,

    PRIMARY KEY (id)
);

# Modelagem DB Discerno PET 🐶

# Usuário
# 1 - nome
# 2 - email
# 3 - emailRecuperacao
# 4 - senha
# 5 - telefone (opcional)
# 6 - endereço (opcional?)
# 7 - cpf (opcional)
# 8 - foto(opcional)
# 9 - Nascimento(opcional)
#
# Pet  (a ser cadastrado)
# 1 - Foto (png/jpeg/outro)
# 2 - Nome
# 3 - Idade(Nascimenti)
# 4 - Raça (previamente cadastradas)
#
# Pet (treinamento Ia)
# 1-Nome
# 2-Foto (png/jpeg/outro)
#
# CadastroCNPJ - (PetShops/Veterinários/etc)
# 1 - Nome
# 2 - CNPJ (Obrigatório)
# 3 - Email
# 4 - EmailRecuperacao
# 5 - Senha
# 6 - Contato1
# 7 - Contato2
# 8 - Contato3
# 9 - Foto (png/jpeg/outro)
