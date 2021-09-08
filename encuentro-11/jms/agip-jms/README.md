# Proyecto agip-jms 

Este proyecto tiene por intención mostrar la integración de Quarkus con JMS.

En este caso utiliizamos ActiveMQ (Artemis)

## Ejecutar ActiveMQ Artemis
Se puede ejectuar un artemis webserver instalado previamente o por medio de Docker

```bash
docker run -it --rm -p 8161:8161 -p 61616:61616 -e ARTEMIS_USERNAME=agip-jms -e ARTEMIS_PASSWORD=agip-jms vromero/activemq-artemis:2.9.0-alpine
```
Consola Artemis: http://localhost:8161/console

usuario: **agip-jms**

password: **agip-jms**

## Ejecución Quarkus en Desarrollo

```shell script
mvn compile quarkus:dev
```

## Test URL

Consultar la URL:
**http://localhost:8080/**
