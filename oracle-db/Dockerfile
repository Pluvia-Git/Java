FROM container-registry.oracle.com/database/express:21.3.0-xe

# Usa o usuário padrão da imagem (oracle)
USER oracle

# Define o diretório de trabalho (recomendado, opcional)
WORKDIR /opt/oracle

# Define senha do Oracle (repetido, mas mantém por clareza)
ENV ORACLE_PWD=123456

# Copia o script SQL de inicialização
COPY init.sql /opt/oracle/scripts/startup/init.sql

# Expõe as portas padrão do Oracle XE
EXPOSE 1521 5500
