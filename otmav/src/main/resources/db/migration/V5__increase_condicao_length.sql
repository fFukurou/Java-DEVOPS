-- V5__increase_condicao_length.sql

ALTER TABLE moto MODIFY (condicao VARCHAR2(255));

COMMIT;