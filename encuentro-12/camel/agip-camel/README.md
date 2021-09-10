# Proyecto AGIP CAMEL

## Ejecutar Servidor FTP

You can run your application in dev mode that enables live coding using:
```shell script
docker run -ti --rm -p 2222:2222 \
    -e PASSWORD_ACCESS=true \
    -e USER_NAME=ftpuser \
    -e USER_PASSWORD=ftppassword \
    -e DOCKER_MODS=linuxserver/mods:openssh-server-openssh-client \
    linuxserver/openssh-server
```

## Ejecutar Quarkus

Ejecutar la aplicaciónn en Quarkus
```shell script
mvn clean compile quarkus:dev
```
