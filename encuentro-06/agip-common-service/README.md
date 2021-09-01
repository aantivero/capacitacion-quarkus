# Proyecto agip-common-service

##Componentes
- QUARKUS
- RESTEasy Reactive
- Hibernate Panache ORM Reactive
- Postgres Driver Reactive
- Docker Postgresql

## Ejecutar BBDD en Docker
```
docker run -it --rm=true --name agip_test -e POSTGRES_USER=agip_test -e POSTGRES_PASSWORD=agip_test -e POSTGRES_DB=agip_test -p 5432:5432 postgres:13.3
```

## Ejecutar aplicación en Dev Mode

```shell script
./mvnw compile quarkus:dev
```

## Compilación

```shell script
./mvnw package
```
Ejecutar `java -jar target/quarkus-app/quarkus-run.jar`.

## Compilación Nativa

```shell script
./mvnw package -Pnative
```
Ejecutar: `./target/agip-common-service-1.0.0-SNAPSHOT-runner`
