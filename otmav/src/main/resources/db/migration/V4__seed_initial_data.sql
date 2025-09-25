
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
