-- V1__drop_tables_and_sequences.sql
-- DROP TABLES
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE moto CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN 
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE setor CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE patio CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE motorista CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE funcionario CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE filial CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE regiao CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE situacao CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE modelo CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE endereco CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE dados CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE token_blacklist CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

-- DROP SEQUENCES
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE dados_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN 
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE endereco_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE modelo_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE situacao_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE regiao_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE filial_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE funcionario_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE motorista_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE patio_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE setor_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE moto_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_token_blacklist';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

-- V2__create_core_tables.sql
-- Tabelas: dados, endereco, modelo, situacao, regiao, filial, funcionario, motorista

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
    id_filial   NUMBER(17) PRIMARY KEY,
    nome_filial VARCHAR2(150),
    id_endereco NUMBER(20),
    CONSTRAINT fk_filial_endereco FOREIGN KEY (id_endereco)
        REFERENCES endereco(id_endereco)
        ON DELETE SET NULL
);

CREATE TABLE funcionario (
    id_func   NUMBER(17) PRIMARY KEY,
    cargo     VARCHAR2(100) NOT NULL,
    id_dados  NUMBER(15) NOT NULL,
    id_filial NUMBER(17),
    CONSTRAINT fk_func_dados FOREIGN KEY (id_dados)
        REFERENCES dados(id_dados),
    CONSTRAINT fk_func_filial FOREIGN KEY (id_filial)
        REFERENCES filial(id_filial)
        ON DELETE SET NULL
);

CREATE TABLE motorista (
    id_motorista NUMBER(15) PRIMARY KEY,
    plano        VARCHAR2(40) NOT NULL,
    id_dados     NUMBER(15) NOT NULL,
    CONSTRAINT fk_motorista_dados FOREIGN KEY (id_dados)
        REFERENCES dados(id_dados)
);

COMMIT;

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

-- V4__create_sequences_and_token_blacklist

-- 1) CREATE SEQUENCES
DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'DADOS_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE DADOS_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'MOTORISTA_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE MOTORISTA_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'FUNCIONARIO_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE FUNCIONARIO_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'ENDERECO_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE ENDERECO_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'REGIAO_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE REGIAO_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'FILIAL_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE FILIAL_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'PATIO_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE PATIO_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'SETOR_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE SETOR_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'MOTO_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE MOTO_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'MODELO_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE MODELO_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;

    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'SITUACAO_SEQ';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE SITUACAO_SEQ START WITH 1000 INCREMENT BY 1 NOCACHE';
    END IF;


    SELECT COUNT(*) INTO v_count FROM user_sequences WHERE sequence_name = 'SEQ_TOKEN_BLACKLIST';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_TOKEN_BLACKLIST START WITH 1 INCREMENT BY 1 NOCACHE';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/


DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM user_tables WHERE table_name = 'TOKEN_BLACKLIST';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE q'{
            CREATE TABLE token_blacklist (
                id_token    NUMBER(19) PRIMARY KEY,
                token       VARCHAR2(2000) NOT NULL UNIQUE,
                expires_at  TIMESTAMP WITH TIME ZONE NOT NULL
            )
        }';

        BEGIN
            SELECT COUNT(*) INTO v_count
            FROM user_tab_cols
            WHERE table_name = 'TOKEN_BLACKLIST'
              AND column_name = 'ID_TOKEN'
              AND identity_column = 'YES';
            IF v_count = 0 THEN
                EXECUTE IMMEDIATE 'ALTER TABLE token_blacklist MODIFY (id_token DEFAULT SEQ_TOKEN_BLACKLIST.NEXTVAL)';
            END IF;
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
-- 2) NEXVALS
DECLARE
    v_is_identity NUMBER;
BEGIN
    -- DADOS
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'DADOS' AND column_name = 'ID_DADOS' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE dados MODIFY (id_dados DEFAULT DADOS_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- MOTORISTA
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'MOTORISTA' AND column_name = 'ID_MOTORISTA' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE motorista MODIFY (id_motorista DEFAULT MOTORISTA_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- FUNCIONARIO
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'FUNCIONARIO' AND column_name = 'ID_FUNC' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE funcionario MODIFY (id_func DEFAULT FUNCIONARIO_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- ENDERECO
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'ENDERECO' AND column_name = 'ID_ENDERECO' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE endereco MODIFY (id_endereco DEFAULT ENDERECO_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- REGIAO
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'REGIAO' AND column_name = 'ID_REGIAO' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE regiao MODIFY (id_regiao DEFAULT REGIAO_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- FILIAL
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'FILIAL' AND column_name = 'ID_FILIAL' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE filial MODIFY (id_filial DEFAULT FILIAL_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- PATIO
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'PATIO' AND column_name = 'ID_PATIO' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE patio MODIFY (id_patio DEFAULT PATIO_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- SETOR
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'SETOR' AND column_name = 'ID_SETOR' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE setor MODIFY (id_setor DEFAULT SETOR_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- MOTO
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'MOTO' AND column_name = 'ID_MOTO' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE moto MODIFY (id_moto DEFAULT MOTO_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- MODELO
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'MODELO' AND column_name = 'ID_MODELO' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE modelo MODIFY (id_modelo DEFAULT MODELO_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

    -- SITUACAO
    SELECT COUNT(*) INTO v_is_identity
    FROM user_tab_cols
    WHERE table_name = 'SITUACAO' AND column_name = 'ID_SITUACAO' AND identity_column = 'YES';
    IF v_is_identity = 0 THEN
        BEGIN
            EXECUTE IMMEDIATE 'ALTER TABLE situacao MODIFY (id_situacao DEFAULT SITUACAO_SEQ.NEXTVAL)';
        EXCEPTION WHEN OTHERS THEN NULL;
        END;
    END IF;

EXCEPTION WHEN OTHERS THEN
    NULL;
END;
/

-- V5__increase_condicao_length.sql

ALTER TABLE moto MODIFY (condicao VARCHAR2(255));

COMMIT;

-- V6__seed_initial_data.sql

INSERT INTO endereco (id_endereco, numero, estado, codigo_pais, codigo_postal, complemento, rua)
VALUES (ENDERECO_SEQ.NEXTVAL, 100, 'SP', 'BR', '01234-000', 'Apt 101', 'Rua das Flores');

INSERT INTO regiao (id_regiao, localizacao, area)
VALUES (REGIAO_SEQ.NEXTVAL, 'POLYGON((-46.0 -23.0, -46.1 -23.0, -46.1 -23.1, -46.0 -23.1, -46.0 -23.0))', 123.45);

INSERT INTO filial (id_filial, nome_filial, id_endereco)
VALUES (FILIAL_SEQ.NEXTVAL, 'Filial Centro', (SELECT id_endereco FROM endereco WHERE rownum = 1));

INSERT INTO situacao (id_situacao, nome, descricao, status) VALUES (SITUACAO_SEQ.NEXTVAL, 'Disponível', 'Moto disponível', 'ATIVO');
INSERT INTO situacao (id_situacao, nome, descricao, status) VALUES (SITUACAO_SEQ.NEXTVAL, 'Em Manutenção', 'Moto em manutenção', 'MANUTENCAO');

INSERT INTO modelo (id_modelo, nome_modelo, frenagem, sis_partida, tanque, tipo_combustivel, consumo)
VALUES (MODELO_SEQ.NEXTVAL, 'ModelOne', 'ABS', 'Eletrico', 12, 'Gasolina', 30);

INSERT INTO modelo (id_modelo, nome_modelo, frenagem, sis_partida, tanque, tipo_combustivel, consumo)
VALUES (MODELO_SEQ.NEXTVAL, 'ModelTwo', 'Drum', 'Manual', 8, 'Flex', 25);

COMMIT;

-- V7__more_inserts.sql

-- Endereco (5)
INSERT INTO endereco (id_endereco, numero, estado, codigo_pais, codigo_postal, complemento, rua)
VALUES (1, 101, 'SP', 'BR', '01000-000', 'Bloco A', 'Rua das Flores');
INSERT INTO endereco VALUES (2, 202, 'RJ', 'BR', '20000-000', 'Casa 2', 'Avenida Atlântica');
INSERT INTO endereco VALUES (3, 303, 'MG', 'BR', '30000-000', 'Sala 12', 'Rua das Palmeiras');
INSERT INTO endereco VALUES (4, 404, 'RS', 'BR', '90000-000', 'Andar 4', 'Rua Bento Gonçalves');
INSERT INTO endereco VALUES (5, 505, 'BA', 'BR', '40000-000', 'Ap 501', 'Avenida Salvador');

-- Modelo (5)
INSERT INTO modelo VALUES (1, 'Model A', 'ABS', 'Elétrico', 12, 'Gasolina', 30);
INSERT INTO modelo VALUES (2, 'Model B', 'Tambor', 'Manual', 10, 'Etanol', 25);
INSERT INTO modelo VALUES (3, 'Model C', 'ABS', 'Manual', 15, 'Flex', 28);
INSERT INTO modelo VALUES (4, 'Model D', 'Disco', 'Elétrico', 20, 'Diesel', 40);
INSERT INTO modelo VALUES (5, 'Model E', 'ABS', 'Automático', 8, 'Gasolina', 22);

-- Situacao (5)
INSERT INTO situacao VALUES (1, 'Disponível', 'Moto pronta para uso', 'ATIVO');
INSERT INTO situacao VALUES (2, 'Em manutenção', 'Moto em oficina', 'MANUTENCAO');
INSERT INTO situacao VALUES (3, 'Reservada', 'Moto reservada para cliente', 'PENDENTE');
INSERT INTO situacao VALUES (4, 'Indisponível', 'Moto não disponível', 'INATIVO');
INSERT INTO situacao VALUES (5, 'Vendida', 'Moto já vendida', 'FINALIZADO');

-- Regiao (5)
INSERT INTO regiao VALUES (1, 'POLYGON((-46 -23, -46.1 -23, -46.1 -23.1, -46 -23.1, -46 -23))', 100);
INSERT INTO regiao VALUES (2, 'POLYGON((-43 -22, -43.1 -22, -43.1 -22.1, -43 -22.1, -43 -22))', 200);
INSERT INTO regiao VALUES (3, 'POLYGON((-44 -19, -44.1 -19, -44.1 -19.1, -44 -19.1, -44 -19))', 150);
INSERT INTO regiao VALUES (4, 'POLYGON((-51 -30, -51.1 -30, -51.1 -30.1, -51 -30.1, -51 -30))', 180);
INSERT INTO regiao VALUES (5, 'POLYGON((-38 -12, -38.1 -12, -38.1 -12.1, -38 -12.1, -38 -12))', 220);

-- Filial (5)
INSERT INTO filial VALUES (1, 'Filial Centro SP', 1);
INSERT INTO filial VALUES (2, 'Filial Copacabana', 2);
INSERT INTO filial VALUES (3, 'Filial BH', 3);
INSERT INTO filial VALUES (4, 'Filial Porto Alegre', 4);
INSERT INTO filial VALUES (5, 'Filial Salvador', 5);

-- Patio (5) 
INSERT INTO patio VALUES (1, 50, 100, 1, 1);
INSERT INTO patio VALUES (2, 30, 80, 2, 2);
INSERT INTO patio VALUES (3, 60, 120, 3, 3);
INSERT INTO patio VALUES (4, 40, 90, 4, 4);
INSERT INTO patio VALUES (5, 25, 60, 5, 5);

-- Setor (5) 
INSERT INTO setor VALUES (1, 10, 20, 'Setor A', 'Área principal', 'Azul', 1, 1);
INSERT INTO setor VALUES (2, 12, 25, 'Setor B', 'Estoque', 'Verde', 2, 2);
INSERT INTO setor VALUES (3, 15, 30, 'Setor C', 'Revisão', 'Amarelo', 3, 3);
INSERT INTO setor VALUES (4, 20, 35, 'Setor D', 'Exposição', 'Vermelho', 4, 4);
INSERT INTO setor VALUES (5, 8, 15, 'Setor E', 'Entregas', 'Preto', 5, 5);

-- Moto (5) 
INSERT INTO moto VALUES (1, 'ABC-1234', 'CHASSI001', 'Disponível', 'SP-Centro', NULL, 1, 1, 1);
INSERT INTO moto VALUES (2, 'DEF-5678', 'CHASSI002', 'Revisão', 'RJ-Copacabana', NULL, 2, 2, 2);
INSERT INTO moto VALUES (3, 'GHI-9012', 'CHASSI003', 'Reservada', 'MG-BH', NULL, 3, 3, 3);
INSERT INTO moto VALUES (4, 'JKL-3456', 'CHASSI004', 'Indisponível', 'RS-Porto Alegre', NULL, 4, 4, 4);
INSERT INTO moto VALUES (5, 'MNO-7890', 'CHASSI005', 'Vendida', 'BA-Salvador', NULL, 5, 5, 5);

COMMIT;

-- V8__funcionario_admin_inserts.sql

INSERT INTO dados VALUES (8000, 44163064826, 1112223334445, 'admin@gmail.com', '$2a$10$QqX16Cs4jdCFm3Ss5mQaKOh0w.iouIwr.LPKYDldrCutMuHd3qEMu', 'Admin');
INSERT INTO funcionario VALUES (8000, 'Admin', 8000, 4);

INSERT INTO dados VALUES (9000, 11122233344, 9998887776665, 'teste@gmail.com', '$2a$10$B4ApDgxkmpPnejflGHGOdeLgX0DsI6rpWjoUjpjwHKD4.ESgdwwke', 'Teste');
INSERT INTO funcionario VALUES (9000, 'Teste', 9000, 4);