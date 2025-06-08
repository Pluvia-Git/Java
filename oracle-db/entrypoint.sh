#!/bin/bash
set -e

# Espera o Oracle estar disponível
until echo exit | sqlplus -s sys/$ORACLE_PWD@localhost:1521/XEPDB1 as sysdba; do
  echo "Waiting for Oracle to start..."
  sleep 5
done

# Executa o script de inicialização
echo "Running init.sql..."
sqlplus sys/$ORACLE_PWD@localhost:1521/XEPDB1 as sysdba @/home/oracledocker/init.sql

# Mantém o container rodando (se sua imagem já faz isso, pode remover essa linha)
tail -f /dev/null
