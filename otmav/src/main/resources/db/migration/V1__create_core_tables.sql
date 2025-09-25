-- Core tables: dados, endereco, modelo, situacao, regiao, filial, funcionario, motorista


CREATE TABLE dados (
    id_dados NUMBER(15) PRIMARY KEY,
    cpf      VARCHAR2(11) NOT NULL,
    telefone VARCHAR2(13),
    email    VARCHAR2(255),
    senha    VARCHAR2(200),
    nome     VARCHAR2(150) NOT NULL
);

CREATE TABLE endereco (
    id_endereco      NUMBER(20) PRIMARY KEY,
    numero           NUMBER(4) NOT NULL,
    estado           VARCHAR2(30) NOT NULL,
    codigo_pais      VARCHAR2(50) NOT NULL,
    codigo_postal    VARCHAR2(50) NOT NULL,
    complemento      VARCHAR2(150),
    rua              VARCHAR2(100) NOT NULL
);

CREATE TABLE modelo (
    id_modelo        NUMBER(15) PRIMARY KEY,
    nome_modelo      VARCHAR2(25) NOT NULL,
    frenagem         VARCHAR2(50),
    sis_partida      VARCHAR2(100),
    tanque           NUMBER(3) NOT NULL,
    tipo_combustivel VARCHAR2(50) NOT NULL,
    consumo          NUMBER(4) NOT NULL
);

CREATE TABLE situacao (
    id_situacao      NUMBER(14) PRIMARY KEY,
    nome             VARCHAR2(250) NOT NULL,
    descricao        VARCHAR2(250),
    status           VARCHAR2(50) NOT NULL
);

CREATE TABLE regiao (
    id_regiao   NUMBER(30) PRIMARY KEY,
    localizacao VARCHAR2(4000) NOT NULL,
    area        NUMBER(7) NOT NULL
);

CREATE TABLE filial (
    id_filial            NUMBER(17) PRIMARY KEY,
    nome_filial          VARCHAR2(150),
    id_endereco          NUMBER(20)
);

ALTER TABLE filial
ADD CONSTRAINT fk_filial_endereco FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco);

CREATE TABLE funcionario (
    id_func      NUMBER(17) PRIMARY KEY,
    cargo        VARCHAR2(100) NOT NULL,
    id_dados     NUMBER(15),
    id_filial    NUMBER(17)
);

ALTER TABLE funcionario
ADD CONSTRAINT fk_func_dados FOREIGN KEY (id_dados) REFERENCES dados(id_dados);

ALTER TABLE funcionario
ADD CONSTRAINT fk_func_filial FOREIGN KEY (id_filial) REFERENCES filial(id_filial);


CREATE TABLE motorista (
    id_motorista NUMBER(15) PRIMARY KEY,
    plano        VARCHAR2(40) NOT NULL,
    id_dados     NUMBER(15)
);

ALTER TABLE motorista
ADD CONSTRAINT fk_motorista_dados FOREIGN KEY (id_dados) REFERENCES dados(id_dados);
