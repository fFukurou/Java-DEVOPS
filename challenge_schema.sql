drop table dados cascade constraints;
drop table modelo cascade constraints;
drop table situacao cascade constraints;
drop table endereco cascade constraints;
drop table regiao cascade constraints;
drop table filial cascade constraints;
drop table patio cascade constraints;
drop table funcionario cascade constraints;
drop table motorista cascade constraints;
drop table moto cascade constraints;
drop table setor cascade constraints;

DROP SEQUENCE SEQ_TOKEN_BLACKLIST;

drop table TOKEN_BLACKLIST;

DROP TABLE auditoria_moto CASCADE CONSTRAINTS;
DROP SEQUENCE seq_auditoria;

CREATE TABLE dados (
    id_dados NUMBER(15) CONSTRAINT id_dados_pk PRIMARY KEY,
    cpf      VARCHAR2(11) NOT NULL,
    telefone VARCHAR2(13),
    email    VARCHAR2(255),
    senha    VARCHAR2(200),
    nome     VARCHAR2(150) NOT NULL
);

CREATE TABLE modelo (
    id_modelo        NUMBER(15) CONSTRAINT id_modelo_pk PRIMARY KEY,
    nome_modelo      VARCHAR2(25) NOT NULL,
    frenagem          VARCHAR2(50),
    sis_partida      VARCHAR2(100),
    tanque           NUMBER(3) NOT NULL,
    tipo_combustivel VARCHAR2(50) NOT NULL,
    consumo          NUMBER(4) NOT NULL
);

CREATE TABLE situacao (
    id_situacao      NUMBER(14) CONSTRAINT id_situacao_pk PRIMARY KEY,
    nome             VARCHAR2(250) NOT NULL,
    descricao        VARCHAR2(250),
    status           VARCHAR2(50) NOT NULL
);

CREATE TABLE endereco (
    id_endereco      NUMBER(20) CONSTRAINT id_endereco_pk PRIMARY KEY,
    numero           NUMBER(4) NOT NULL,
    estado           VARCHAR2(30) NOT NULL,
    codigo_pais      VARCHAR2(50) NOT NULL,
    codigo_postal    VARCHAR2(50) NOT NULL,
    complemento      VARCHAR2(150),
    rua              VARCHAR2(100) NOT NULL
);

CREATE TABLE REGIAO 
    ( 
     ID_REGIAO   NUMBER (30)  CONSTRAINT id_regiao_pk PRIMARY KEY , 
     LOCALIZACAO VARCHAR2(255)  NOT NULL , 
     AREA        NUMBER (7)  NOT NULL 
    );
    
CREATE TABLE filial (
    id_filial            NUMBER(17) CONSTRAINT id_filial_pk PRIMARY KEY,
    nome_filial          VARCHAR2(150),
    id_endereco          NUMBER(20) CONSTRAINT id_endereco_fk REFERENCES endereco
);

CREATE TABLE funcionario (
    id_func          NUMBER(17) CONSTRAINT id_func_pk PRIMARY KEY,
    cargo            VARCHAR2(100) NOT NULL,
    id_dados        NUMBER(15) CONSTRAINT id_dados_funcionario_fk REFERENCES dados,
    id_filial       NUMBER(17) CONSTRAINT id_filial_fk REFERENCES filial
);

CREATE TABLE motorista (
    id_motorista NUMBER(15) CONSTRAINT id_motorista_pk PRIMARY KEY,
    plano        VARCHAR2(20) NOT NULL,
    id_dados        NUMBER(15) CONSTRAINT id_dados_motorista_fk REFERENCES dados
);

CREATE TABLE patio (
    id_patio     NUMBER(30) CONSTRAINT pk_patio PRIMARY KEY,
    total_motos          NUMBER(5) NOT NULL,
    capacidade_moto      NUMBER(5) NOT NULL,
    id_filial            NUMBER(17) CONSTRAINT id_filial_patio_fk REFERENCES filial,
    id_regiao           NUMBER(30) CONSTRAINT id_regiao_patio_fk REFERENCES regiao
);

CREATE TABLE setor (
    id_setor     NUMBER(17) CONSTRAINT pk_setor  PRIMARY KEY,
    qtd_moto     NUMBER(3) NOT NULL,
    capacidade   NUMBER(3) NOT NULL,
    nome_setor   VARCHAR2(250),
    descricao    VARCHAR2(250),
    cor    VARCHAR2(20) NOT NULL,
    id_patio     NUMBER(17) CONSTRAINT id_patio REFERENCES patio,
    id_regiao    NUMBER(30) CONSTRAINT id_regiao_setor_fk REFERENCES regiao
);


CREATE TABLE moto (
    id_moto       NUMBER(16) CONSTRAINT id_moto_pk PRIMARY KEY,
    placa         VARCHAR2(7),
    chassi        VARCHAR2(17),
    condicao      VARCHAR2(255) NOT NULL,
    localizacao_moto VARCHAR2(255)  NOT NULL,
    id_motorista  NUMBER(15) CONSTRAINT id_motorista_fk REFERENCES motorista,
    id_modelo     NUMBER(15) CONSTRAINT id_modelo_fk REFERENCES modelo,
    id_setor      NUMBER(17) CONSTRAINT id_setor_moto_fk REFERENCES setor,
    id_situacao   NUMBER(14) CONSTRAINT id_situacao_moto_fk REFERENCES situacao
);

CREATE TABLE token_blacklist (
    id_token    NUMBER(19) PRIMARY KEY,
    token       VARCHAR2(2000) NOT NULL UNIQUE,
    expires_at  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE SEQUENCE SEQ_TOKEN_BLACKLIST START WITH 1 INCREMENT BY 1 NOCACHE;

ALTER TABLE TOKEN_BLACKLIST MODIFY id_token DEFAULT SEQ_TOKEN_BLACKLIST.NEXTVAL;

------------ INSERTS -------------

INSERT INTO dados VALUES (1, '12345678901', '11999999999', 'joao@email.com', 'Joao@123', 'Joao Silva');
INSERT INTO dados VALUES (2, '23456789012', '21988888888', 'maria@email.com', 'MariaO23', 'Maria Oliveira');
INSERT INTO dados VALUES (3, '34567890123', '31977777777', 'carlos@email.com', 'Carlos@010203', 'Carlos Lima');
INSERT INTO dados VALUES (4, '45678901234', '41966666666', 'ana@email.com', 'Paula23', 'Ana Paula');
INSERT INTO dados VALUES (5, '56789012345', '51955555555', 'lucas@email.com', 'Lucas1129', 'Lucas Mendes');
INSERT INTO dados VALUES (6, '67890123456', '61944444444', 'fernanda@email.com', 'F@4587', 'Fernanda Rocha');
INSERT INTO dados VALUES (7, '78901234567', '71933333333', 'rafael@email.com', 'Rafinha@60', 'Rafael Souza');
INSERT INTO dados VALUES (8, '89012345678', '81922222222', 'juliana@email.com', 'julianaC_23', 'Juliana Castro');
INSERT INTO dados VALUES (9, '90123456789', '91911111111', 'andre@email.com', 'AndresaBarbosa@123', 'Andra Barbosa');
INSERT INTO dados VALUES (10, '01234567890', '11900000000', 'patricia@email.com', '1234Patricia', 'Patricia Ferreira');


INSERT INTO modelo VALUES (1, 'Honda CG 160', 'FreioADisco', 'PartidaEletrica ', 16, 'Gasolina', 40);
INSERT INTO modelo VALUES (2, 'Yamaha Factor', 'FreioATambor', 'PartidaPedal', 15, 'Gasolina', 38);
INSERT INTO modelo VALUES (3, 'Suzuki Yes', 'FreioCombinado', 'PartidaHibrida', 14, 'Gasolina', 35);
INSERT INTO modelo VALUES (4, 'Kawasaki Ninja', 'FreioABS', 'PartidaManualComEmbreagem', 17, 'Gasolina', 28);
INSERT INTO modelo VALUES (5, 'Harley Sportster', 'FreioHidraulico', 'PartidaStartStop', 20, 'Gasolina', 25);


INSERT INTO situacao VALUES (1, 'Motor quebrado', 'Aguardando processamento', 'Ativo');
INSERT INTO situacao VALUES (2, 'Sem placa', 'Processo em execução', 'Ativo');
INSERT INTO situacao VALUES (3, 'Concluído', 'Processo finalizado com sucesso', 'Ativo');
INSERT INTO situacao VALUES (4, 'Cancelado', 'Processo cancelado pelo usuário ou sistema', 'Inativo');
INSERT INTO situacao VALUES (5, 'Manutencao', 'Em manutencao no momento', 'Manutencao');


INSERT INTO endereco VALUES (1, 100, 'SP', 'BR','0932103021', 'Apto 101', 'Rua das Motos');
INSERT INTO endereco VALUES (2, 200, 'RJ', 'US','032103120321', NULL, 'Avenida das Engrenagens');
INSERT INTO endereco VALUES (3, 300, 'MG', 'BR','231322300', 'Casa', 'Rua dos Motociclistas');
INSERT INTO endereco VALUES (4, 400, 'RS', 'BR','42134210232', 'Fundos', 'Rua da Velocidade');
INSERT INTO endereco VALUES (5, 500, 'PR', 'BR','3213123210', NULL, 'Rua da Liberdade');








INSERT INTO filial VALUES (1, 'Filial Zona Sul', 1);
INSERT INTO filial VALUES (2, 'Filial Centro', 2);
INSERT INTO filial VALUES (3, 'Filial Norte', 3);
INSERT INTO filial VALUES (4, 'Filial Leste', 4);
INSERT INTO filial VALUES (5, 'Filial Oeste', 5);


INSERT INTO funcionario VALUES (101, 'Gerente', 1, 1);
INSERT INTO funcionario VALUES (102, 'Mecanico', 2, 2);
INSERT INTO funcionario VALUES (103, 'Vendedor', 3, 3);
INSERT INTO funcionario VALUES (104, 'Auxiliar', 4, 4);
INSERT INTO funcionario VALUES (105, 'Analista', 5, 5);


INSERT INTO motorista VALUES (201, 'MottuCompleto', 6);
INSERT INTO motorista VALUES (202, 'MottuConquiste', 7);
INSERT INTO motorista VALUES (203, 'MottuCompleto', 8);
INSERT INTO motorista VALUES (204, 'MottuConquiste', 9);
INSERT INTO motorista VALUES (205, 'MottuCompleto', 10);


INSERT INTO patio VALUES (1, 30, 50, 1, 1);
INSERT INTO patio VALUES (2, 25, 40, 2, 2);
INSERT INTO patio VALUES (3, 20, 35, 3, 3);
INSERT INTO patio VALUES (4, 15, 30, 4, 4);
INSERT INTO patio VALUES (5, 10, 25, 5, 5);


INSERT INTO setor VALUES (1, 10, 15, 'Setor A', 'Setor de motos leves', '#E63946', 1, 6);
INSERT INTO setor VALUES (2, 8, 12, 'Setor B', 'Setor de motos médias','#2A9D8F', 2, 7);
INSERT INTO setor VALUES (3, 5, 8, 'Setor C', 'Setor de motos pesadas','#264653', 3, 8);
INSERT INTO setor VALUES (4, 12, 20, 'Setor D', 'Setor de testes','#E9C46A', 4, 9);
INSERT INTO setor VALUES (5, 6, 10, 'Setor E', 'Setor de manutenção','#9B5DE5', 5, 10);


----------------------------------------Sequence-------------------------------------
DROP SEQUENCE DADOS_SEQ;
DROP SEQUENCE MOTORISTA_SEQ;
DROP SEQUENCE FUNCIONARIO_SEQ;
DROP SEQUENCE ENDERECO_SEQ;
DROP SEQUENCE REGIAO_SEQ;
DROP SEQUENCE FILIAL_SEQ;
DROP SEQUENCE PATIO_SEQ;
DROP SEQUENCE SETOR_SEQ;
DROP SEQUENCE MOTO_SEQ;
DROP SEQUENCE MODELO_SEQ;
DROP SEQUENCE SITUACAO_SEQ;



CREATE SEQUENCE DADOS_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE MOTORISTA_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE FUNCIONARIO_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE ENDERECO_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE REGIAO_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE FILIAL_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE PATIO_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE SETOR_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE MOTO_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE MODELO_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE SITUACAO_SEQ START WITH 1000 INCREMENT BY 1;


ALTER TABLE dados MODIFY id_dados DEFAULT DADOS_SEQ.NEXTVAL;
ALTER TABLE motorista MODIFY id_motorista DEFAULT MOTORISTA_SEQ.NEXTVAL;
ALTER TABLE funcionario MODIFY id_func DEFAULT FUNCIONARIO_SEQ.NEXTVAL;
ALTER TABLE endereco MODIFY id_endereco DEFAULT ENDERECO_SEQ.NEXTVAL;
ALTER TABLE REGIAO MODIFY ID_REGIAO DEFAULT REGIAO_SEQ.NEXTVAL;
ALTER TABLE filial MODIFY id_filial DEFAULT FILIAL_SEQ.NEXTVAL;
ALTER TABLE patio MODIFY id_patio DEFAULT PATIO_SEQ.NEXTVAL;
ALTER TABLE setor MODIFY id_setor DEFAULT SETOR_SEQ.NEXTVAL;
ALTER TABLE moto MODIFY id_moto DEFAULT MOTO_SEQ.NEXTVAL;
ALTER TABLE modelo MODIFY id_modelo DEFAULT MODELO_SEQ.NEXTVAL;
ALTER TABLE situacao MODIFY id_situacao DEFAULT SITUACAO_SEQ.NEXTVAL;