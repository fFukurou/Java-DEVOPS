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
