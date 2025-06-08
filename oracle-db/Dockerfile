FROM container-registry.oracle.com/database/express:21.3.0-xe

RUN useradd -ms /bin/bash oracledocker

WORKDIR /home/oracledocker

ENV ORACLE_PWD=123456

COPY init.sql /home/oracledocker/
COPY entrypoint.sh /home/oracledocker/

RUN chmod +x /home/oracledocker/entrypoint.sh

EXPOSE 1521 5500

USER oracledocker

ENTRYPOINT ["/home/oracledocker/entrypoint.sh"]
