-- Criar o usuário da aplicação
CREATE USER app_user IDENTIFIED BY app_pass;
GRANT CONNECT, RESOURCE TO app_user;
ALTER USER app_user QUOTA UNLIMITED ON USERS;

-- Trocar para o usuário criado
ALTER SESSION SET CURRENT_SCHEMA = app_user;

-- Tabela de Endereço
CREATE TABLE T_PL_ENDERECO (
                               ID_ENDERECO       INTEGER NOT NULL PRIMARY KEY,
                               ID_CIDADE         VARCHAR(50),
                               ID_BAIRRO         VARCHAR(50),
                               CD_CEP            NUMERIC(8),
                               SG_ESTADO         CHAR(2),
                               DS_LOGRADOURO     VARCHAR(50),
                               VL_LATITUDE       DECIMAL(9,6),
                               VL_LONGITUDE      DECIMAL(9,6)
);

-- Tabela de Usuário
CREATE TABLE T_PL_USUARIO (
                              ID_USUARIO        INTEGER NOT NULL,
                              ID_ENDERECO       INTEGER NOT NULL,
                              ID_NOME           VARCHAR(50),
                              ID_EMAIL          VARCHAR(100),
                              CD_CPF            NUMERIC(11),
                              CD_SENHA          VARCHAR(20),
                              CONSTRAINT T_PL_USUARIO_PK PRIMARY KEY (ID_USUARIO, ID_ENDERECO),
                              CONSTRAINT T_PL_USUARIO_FK_ENDERECO FOREIGN KEY (ID_ENDERECO) REFERENCES T_PL_ENDERECO(ID_ENDERECO)
);

-- Tabela de Clima
CREATE TABLE T_PL_CLIMA (
                            ID_CLIMA              NUMBER(3) NOT NULL,
                            ID_ENDERECO           INTEGER NOT NULL,
                            DT_HORARIO            DATE,
                            DS_CONDICAO           VARCHAR2(20),
                            DS_DESCRICAO          VARCHAR2(20),
                            VL_TEMPERATURA        NUMBER(5,2),
                            VL_PRESSAO            NUMBER(4),
                            VL_UMIDADE            NUMBER(2),
                            VL_VELOCIDADE_VENTO   NUMBER(4,2),
                            VL_NUVENS             NUMBER(3),
                            FL_ESP32              NUMBER(1) CHECK (FL_ESP32 IN (0,1)),
                            CONSTRAINT T_PL_CLIMA_PK PRIMARY KEY (ID_CLIMA, ID_ENDERECO),
                            CONSTRAINT T_PL_CLIMA_FK_ENDERECO FOREIGN KEY (ID_ENDERECO) REFERENCES T_PL_ENDERECO(ID_ENDERECO)
);

-- Tabela de Alerta
CREATE TABLE T_PL_ALERTA (
                             ID_ALERTA             INTEGER PRIMARY KEY,
                             ID_USUARIO            INTEGER,
                             ID_ENDERECO           INTEGER,
                             DS_DESCRICAO_ALERTA   VARCHAR2(50),
                             DT_HORARIO            DATE,
                             FL_STATUS             NUMBER(1) CHECK (FL_STATUS IN (0,1)),
                             CONSTRAINT T_PL_ALERTA_FK_USUARIO FOREIGN KEY (ID_USUARIO, ID_ENDERECO) REFERENCES T_PL_USUARIO(ID_USUARIO, ID_ENDERECO)
);
