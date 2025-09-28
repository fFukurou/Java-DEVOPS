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