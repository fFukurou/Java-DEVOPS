-- V3__create_patio_setor_moto.sql
-- Patio, Setor, Moto

CREATE TABLE patio (
    id_patio        NUMBER(30) PRIMARY KEY,
    total_motos     NUMBER(5) NOT NULL,
    capacidade_moto NUMBER(5) NOT NULL,
    id_filial       NUMBER(17),
    id_regiao       NUMBER(30),
    CONSTRAINT fk_patio_filial FOREIGN KEY (id_filial)
        REFERENCES filial(id_filial)
        ON DELETE SET NULL,
    CONSTRAINT fk_patio_regiao FOREIGN KEY (id_regiao)
        REFERENCES regiao(id_regiao)
        ON DELETE SET NULL
);

CREATE TABLE setor (
    id_setor   NUMBER(17) PRIMARY KEY,
    qtd_moto   NUMBER(3) NOT NULL,
    capacidade NUMBER(3) NOT NULL,
    nome_setor VARCHAR2(250),
    descricao  VARCHAR2(250),
    cor        VARCHAR2(20) NOT NULL,
    id_patio   NUMBER(30),
    id_regiao  NUMBER(30),
    CONSTRAINT fk_setor_patio FOREIGN KEY (id_patio)
        REFERENCES patio(id_patio)
        ON DELETE SET NULL,
    CONSTRAINT fk_setor_regiao FOREIGN KEY (id_regiao)
        REFERENCES regiao(id_regiao)
        ON DELETE SET NULL
);

CREATE TABLE moto (
    id_moto          NUMBER(16) PRIMARY KEY,
    placa            VARCHAR2(20),
    chassi           VARCHAR2(50),
    condicao         VARCHAR2(255) NOT NULL,
    localizacao_moto VARCHAR2(4000) NOT NULL,
    id_motorista     NUMBER(15),
    id_modelo        NUMBER(15),
    id_setor         NUMBER(17),
    id_situacao      NUMBER(14),
    CONSTRAINT fk_moto_motorista FOREIGN KEY (id_motorista)
        REFERENCES motorista(id_motorista)
        ON DELETE SET NULL,
    CONSTRAINT fk_moto_modelo FOREIGN KEY (id_modelo)
        REFERENCES modelo(id_modelo)
        ON DELETE SET NULL,
    CONSTRAINT fk_moto_setor FOREIGN KEY (id_setor)
        REFERENCES setor(id_setor)
        ON DELETE SET NULL,
    CONSTRAINT fk_moto_situacao FOREIGN KEY (id_situacao)
        REFERENCES situacao(id_situacao)
        ON DELETE SET NULL
);

COMMIT;