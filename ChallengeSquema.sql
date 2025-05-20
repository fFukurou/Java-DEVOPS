drop table dados cascade constraints;
drop table modelo cascade constraints;
drop table situacao cascade constraints;
drop table endereco cascade constraints;
drop table filial cascade constraints;
drop table patio cascade constraints;
drop table funcionario cascade constraints;
drop table motorista cascade constraints;
drop table moto cascade constraints;
drop table setor cascade constraints;

CREATE TABLE dados (
    id_dados NUMBER(15) CONSTRAINT id_dados_pk PRIMARY KEY,
    cpf      VARCHAR2(11) NOT NULL,
    telefone VARCHAR2(13) NOT NULL,
    email    VARCHAR2(255),
    senha    VARCHAR2(20),
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

CREATE TABLE funcionario (
    id_func          NUMBER(17) CONSTRAINT id_func_pk PRIMARY KEY,
    cargo            VARCHAR2(100) NOT NULL,
    id_dados        NUMBER(15) CONSTRAINT id_dados_funcionario_fk REFERENCES dados
);

CREATE TABLE motorista (
    id_motorista NUMBER(15) CONSTRAINT id_motorista_pk PRIMARY KEY,
    plano        VARCHAR2(20) NOT NULL,
    id_dados        NUMBER(15) CONSTRAINT id_dados_motorista_fk REFERENCES dados
);


CREATE TABLE filial (
    id_filial            NUMBER(17) CONSTRAINT id_filial_pk PRIMARY KEY,
    nome_filial          VARCHAR2(150),
    id_responsavel       NUMBER(7) NOT NULL,
    id_endereco          NUMBER(20) CONSTRAINT id_endereco_fk REFERENCES endereco,
    id_func              NUMBER(17) CONSTRAINT id_funcionario_fk REFERENCES funcionario
);

CREATE TABLE patio (
    id_patio     NUMBER(30) CONSTRAINT pk_patio PRIMARY KEY,
    total_motos          NUMBER(5) NOT NULL,
    capacidade_moto      NUMBER(5) NOT NULL,
    area_patio           NUMBER(8) NOT NULL,
    id_filial            NUMBER(17) CONSTRAINT id_filial_patio_fk REFERENCES filial
);

CREATE TABLE setor (
    id_setor     NUMBER(17) CONSTRAINT pk_setor  PRIMARY KEY,
    qtd_moto     NUMBER(3) NOT NULL,
    capacidade   NUMBER(3) NOT NULL,
    area_setor   NUMBER(5) NOT NULL,
    nome_setor   VARCHAR2(250),
    descricao    VARCHAR2(250),
    id_patio     NUMBER(17) CONSTRAINT id_patio REFERENCES patio
);


CREATE TABLE moto (
    id_moto       NUMBER(16) CONSTRAINT id_moto_pk PRIMARY KEY,
    placa         VARCHAR2(7),
    chassi        VARCHAR2(17),
    condicao      VARCHAR2(8) NOT NULL,
    latitude      VARCHAR2(5) NOT NULL,
    longitude      VARCHAR2(5) NOT NULL,
    id_motorista  NUMBER(15) CONSTRAINT id_motorista_fk REFERENCES motorista,
    id_modelo     NUMBER(15) CONSTRAINT id_modelo_fk REFERENCES modelo,
    id_setor      NUMBER(17) CONSTRAINT id_setor_moto_fk REFERENCES setor,
    id_situacao   NUMBER(14) CONSTRAINT id_situacao_moto_fk REFERENCES situacao
);


------------------------------------------ Inserts -----------------------------------------

INSERT INTO dados VALUES (1, '12345678901', '11999999999', 'joao@email.com', 'senha123', 'Jo?o Silva');
INSERT INTO dados VALUES (2, '23456789012', '21988888888', 'maria@email.com', 'senha123', 'Maria Oliveira');
INSERT INTO dados VALUES (3, '34567890123', '31977777777', 'carlos@email.com', 'senha123', 'Carlos Lima');
INSERT INTO dados VALUES (4, '45678901234', '41966666666', 'ana@email.com', 'senha123', 'Ana Paula');
INSERT INTO dados VALUES (5, '56789012345', '51955555555', 'lucas@email.com', 'senha123', 'Lucas Mendes');
INSERT INTO dados VALUES (6, '67890123456', '61944444444', 'fernanda@email.com', 'senha123', 'Fernanda Rocha');
INSERT INTO dados VALUES (7, '78901234567', '71933333333', 'rafael@email.com', 'senha123', 'Rafael Souza');
INSERT INTO dados VALUES (8, '89012345678', '81922222222', 'juliana@email.com', 'senha123', 'Juliana Castro');
INSERT INTO dados VALUES (9, '90123456789', '91911111111', 'andre@email.com', 'senha123', 'Andr? Barbosa');
INSERT INTO dados VALUES (10, '01234567890', '11900000000', 'patricia@email.com', 'senha123', 'Patr?cia Ferreira');


INSERT INTO modelo VALUES (1, 'Honda CG 160', 'FreioADisco', 'PartidaEletrica ', 16, 'Gasolina', 40);
INSERT INTO modelo VALUES (2, 'Yamaha Factor', 'FreioATambor', 'PartidaPedal', 15, 'Gasolina', 38);
INSERT INTO modelo VALUES (3, 'Suzuki Yes', 'FreioCombinado', 'PartidaHibrida', 14, 'Gasolina', 35);
INSERT INTO modelo VALUES (4, 'Kawasaki Ninja', 'FreioABS', 'PartidaManualComEmbreagem', 17, 'Gasolina', 28);
INSERT INTO modelo VALUES (5, 'Harley Sportster', 'FreioHidraulico', 'PartidaStartStop', 20, 'Gasolina', 25);


INSERT INTO situacao VALUES (1, 'Motor quebrado', 'Aguardando processamento', 'Ativo');
INSERT INTO situacao VALUES (2, 'Sem placa', 'Processo em execu??o', 'Ativo');
INSERT INTO situacao VALUES (3, 'Conclu?do', 'Processo finalizado com sucesso', 'Ativo');
INSERT INTO situacao VALUES (4, 'Cancelado', 'Processo cancelado pelo usu?rio ou sistema', 'Inativo');
INSERT INTO situacao VALUES (5, 'Manutencao', 'Em manutencao no momento', 'Manutencao');


INSERT INTO endereco VALUES (1, 100, 'SP', 'BR','0932103021', 'Apto 101', 'Rua das Motos');
INSERT INTO endereco VALUES (2, 200, 'RJ', 'US','032103120321', NULL, 'Avenida das Engrenagens');
INSERT INTO endereco VALUES (3, 300, 'MG', 'BR','231322300', 'Casa', 'Rua dos Motociclistas');
INSERT INTO endereco VALUES (4, 400, 'RS', 'BR','42134210232', 'Fundos', 'Rua da Velocidade');
INSERT INTO endereco VALUES (5, 500, 'PR', 'BR','3213123210', NULL, 'Rua da Liberdade');


INSERT INTO funcionario VALUES (101, 'Gerente', 1);
INSERT INTO funcionario VALUES (102, 'Mecanico', 2);
INSERT INTO funcionario VALUES (103, 'Vendedor', 3);
INSERT INTO funcionario VALUES (104, 'Auxiliar', 4);
INSERT INTO funcionario VALUES (105, 'Analista', 5);


INSERT INTO motorista VALUES (201, 'MottuCompleto', 6);
INSERT INTO motorista VALUES (202, 'MottuConquiste', 7);
INSERT INTO motorista VALUES (203, 'MottuCompleto', 8);
INSERT INTO motorista VALUES (204, 'MottuConquiste', 9);
INSERT INTO motorista VALUES (205, 'MottuCompleto', 10);


INSERT INTO filial VALUES (1, 'Filial Zona Sul', 50, 1, 101);
INSERT INTO filial VALUES (2, 'Filial Centro', 40, 2, 102);
INSERT INTO filial VALUES (3, 'Filial Norte', 30, 3, 103);
INSERT INTO filial VALUES (4, 'Filial Leste', 35, 4, 104);
INSERT INTO filial VALUES (5, 'Filial Oeste', 45, 5, 105);


INSERT INTO patio VALUES (1, 30, 50, 500, 1);
INSERT INTO patio VALUES (2, 25, 40, 450, 2);
INSERT INTO patio VALUES (3, 20, 35, 400, 3);
INSERT INTO patio VALUES (4, 15, 30, 350, 4);
INSERT INTO patio VALUES (5, 10, 25, 300, 5);


INSERT INTO setor VALUES (1, 10, 15, 200, 'Setor A', 'Setor de motos leves', 1);
INSERT INTO setor VALUES (2, 8, 12, 180, 'Setor B', 'Setor de motos m?dias', 2);
INSERT INTO setor VALUES (3, 5, 8, 150, 'Setor C', 'Setor de motos pesadas', 3);
INSERT INTO setor VALUES (4, 12, 20, 220, 'Setor D', 'Setor de testes', 4);
INSERT INTO setor VALUES (5, 6, 10, 170, 'Setor E', 'Setor de manuten??o', 5);


INSERT INTO moto VALUES (1001, 'ABC1234', '9BWZZZ377VT004251', 'ATIVA', '23.56', '46.64', 201, 1, 1, 1);
INSERT INTO moto VALUES (1002, 'XYZ5678', '8ADZZZ333LV019876', 'INATIVA', '22.97', '43.20', 202, 2, 2, 2);
INSERT INTO moto VALUES (1003, 'KLM4321', '7CVZZZ999FR045678', 'MANUTEN', '21.10', '47.87', 203, 3, 3, 3);
INSERT INTO moto VALUES (1004, 'DEF8765', '1HGCM82633A004352', 'ATIVA', '19.55', '45.32', 204, 4, 4, 4);
INSERT INTO moto VALUES (1005, 'GHI3456', '2FTRX18W1XCA12345', 'INATIVA', '20.78', '44.87', 205, 5, 5, 5);

-------------------------------------------PLSQL--------------------------------------

BEGIN
  FOR reg IN (
    SELECT s.nome AS situacao, COUNT(m.id_moto) AS total_motos
    FROM moto m
    JOIN situacao s ON m.id_situacao = s.id_situacao
    GROUP BY s.nome
    ORDER BY total_motos DESC
  ) LOOP
    DBMS_OUTPUT.PUT_LINE('Situação: ' || reg.situacao || ' - Total de motos: ' || reg.total_motos);
  END LOOP;
END;
/

BEGIN
  FOR reg IN (
    SELECT f.cargo, COUNT(f.id_func) AS total_funcionarios, fi.nome_filial
    FROM funcionario f
    JOIN filial fi ON f.id_func = fi.id_func
    GROUP BY f.cargo, fi.nome_filial
    ORDER BY fi.nome_filial
  ) LOOP
    DBMS_OUTPUT.PUT_LINE('Cargo: ' || reg.cargo || ' - Total: ' || reg.total_funcionarios || ' - Filial: ' || reg.nome_filial);
  END LOOP;
END;
/


-------------------------------------------------------------------------------------------

DECLARE
  CURSOR c_modelo IS
    SELECT consumo FROM (
      SELECT consumo, ROW_NUMBER() OVER (ORDER BY id_modelo) AS rn
      FROM modelo
    );
    
  TYPE consumo_tab IS TABLE OF modelo.consumo%TYPE INDEX BY PLS_INTEGER;
  v_consumos consumo_tab;
  v_count    INTEGER := 0;
BEGIN
  -- Carrega os dados na coleção
  FOR rec IN c_modelo LOOP
    v_count := v_count + 1;
    v_consumos(v_count) := rec.consumo;
  END LOOP;

  -- Exibe os dados com linha anterior e próxima
  FOR i IN 1 .. v_count LOOP
    DBMS_OUTPUT.PUT_LINE(
      'Anterior: ' || NVL(TO_CHAR(v_consumos.EXISTS(i - 1) THEN v_consumos(i - 1)), 'Vazio') || ' | ' ||
      'Atual: ' || v_consumos(i) || ' | ' ||
      'Próximo: ' || NVL(TO_CHAR(v_consumos.EXISTS(i + 1) THEN v_consumos(i + 1)), 'Vazio')
    );
  END LOOP;
END;
/


---------------------------Codigo para gerar ID automatico-----------------------------------------
DROP SEQUENCE seq_dados;
DROP SEQUENCE seq_modelo;
DROP SEQUENCE seq_situacao;
DROP SEQUENCE seq_endereco;
DROP SEQUENCE seq_funcionario;
DROP SEQUENCE seq_motorista;
DROP SEQUENCE seq_filial;
DROP SEQUENCE seq_patio;
DROP SEQUENCE seq_setor;
DROP SEQUENCE seq_moto;


-- SEQUENCES
CREATE SEQUENCE seq_dados START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_modelo START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_situacao START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_endereco START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_funcionario START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_motorista START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_filial START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_patio START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_setor START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_moto START WITH 1 INCREMENT BY 1;

-- TRIGGERS
CREATE OR REPLACE TRIGGER trg_dados
BEFORE INSERT ON dados
FOR EACH ROW
BEGIN
  IF :NEW.id_dados IS NULL THEN
    :NEW.id_dados := seq_dados.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_modelo
BEFORE INSERT ON modelo
FOR EACH ROW
BEGIN
  IF :NEW.id_modelo IS NULL THEN
    :NEW.id_modelo := seq_modelo.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_situacao
BEFORE INSERT ON situacao
FOR EACH ROW
BEGIN
  IF :NEW.id_situacao IS NULL THEN
    :NEW.id_situacao := seq_situacao.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_endereco
BEFORE INSERT ON endereco
FOR EACH ROW
BEGIN
  IF :NEW.id_endereco IS NULL THEN
    :NEW.id_endereco := seq_endereco.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_funcionario
BEFORE INSERT ON funcionario
FOR EACH ROW
BEGIN
  IF :NEW.id_func IS NULL THEN
    :NEW.id_func := seq_funcionario.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_motorista
BEFORE INSERT ON motorista
FOR EACH ROW
BEGIN
  IF :NEW.id_motorista IS NULL THEN
    :NEW.id_motorista := seq_motorista.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_filial
BEFORE INSERT ON filial
FOR EACH ROW
BEGIN
  IF :NEW.id_filial IS NULL THEN
    :NEW.id_filial := seq_filial.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_patio
BEFORE INSERT ON patio
FOR EACH ROW
BEGIN
  IF :NEW.id_patio IS NULL THEN
    :NEW.id_patio := seq_patio.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_setor
BEFORE INSERT ON setor
FOR EACH ROW
BEGIN
  IF :NEW.id_setor IS NULL THEN
    :NEW.id_setor := seq_setor.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_moto
BEFORE INSERT ON moto
FOR EACH ROW
BEGIN
  IF :NEW.id_moto IS NULL THEN
    :NEW.id_moto := seq_moto.NEXTVAL;
  END IF;
END;
/

------ --  EXTRA INSERTS ---------------
---- DADOS

-- 2. João Silva (São Paulo)
INSERT INTO dados (id_dados, cpf, telefone, email, senha, nome)
VALUES (15, '34567890123', '11997776666', 'joao.silva@email.com', 'joao@456', 'João Silva');

-- 3. Carlos Pereira (Rio de Janeiro)
INSERT INTO dados (id_dados, cpf, telefone, email, senha, nome)
VALUES (16, '45678901234', '21995554444', 'carlos.pereira@email.com', 'carlos789', 'Carlos Pereira');

-- 4. Ana Santos (Minas Gerais)
INSERT INTO dados (id_dados, cpf, telefone, email, senha, nome)
VALUES (17, '56789012345', '31993332222', 'ana.santos@email.com', 'ana1011', 'Ana Santos');

-- 5. Pedro Almeida (Bahia)
INSERT INTO dados (id_dados, cpf, telefone, email, senha, nome)
VALUES (18, '67890123456', '71991112233', 'pedro.almeida@email.com', 'pedro1213', 'Pedro Almeida');

-- 6. Luiza Fernandes (Santa Catarina) - Bonus
INSERT INTO dados (id_dados, cpf, telefone, email, senha, nome)
VALUES (19, '78901234567', '47998887777', 'luiza.fernandes@email.com', 'luiza1415', 'Luiza Fernandes');


-------- FUNCIONARIOS
-- 2. João Silva - Mecânico Chefe
INSERT INTO funcionario (id_func, cargo, id_dados)
VALUES (30, 'Mecânico Chefe', 15);

-- 3. Carlos Pereira - Atendente
INSERT INTO funcionario (id_func, cargo, id_dados)
VALUES (35, 'Deus', 16);

-- 4. Ana Santos - Coordenadora de Logística
INSERT INTO funcionario (id_func, cargo, id_dados)
VALUES (45, 'Gerente Supremo', 17);

-- 5. Pedro Almeida - Especialista em Manutenção
INSERT INTO funcionario (id_func, cargo, id_dados)
VALUES (40, 'Especialista Especial', 18);

-- 6. Luiza Fernandes - Supervisora (Bonus)
INSERT INTO funcionario (id_func, cargo, id_dados)
VALUES (50, 'Doutor Gerente', 19);





