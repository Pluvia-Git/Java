FROM openjdk:17

# Requisito 1: Criar usuário não root
RUN useradd -ms /bin/bash appuser

# Requisito 2: Definir diretório de trabalho
WORKDIR /home/appuser/app

# Copiar JAR final para o container
COPY target/pluvia-1.0.jar app.jar

# Requisito 3: Variáveis de ambiente (recebidas do docker-compose)
ENV DB_USER=crud
ENV DB_PASS=crud123
ENV DB_HOST=oracle-db
ENV DB_PORT=1521
ENV DB_SERVICE=XEPDB1

# Requisito 4: Expor porta da aplicação
EXPOSE 8080

# Executar aplicação
USER appuser
CMD ["java", "-jar", "app.jar"]
