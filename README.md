# Lab1 - Programación 3

Repositorio del laboratorio: normalización de datos, PostgreSQL y API REST con Spring Boot.

## Requisitos previos
- JDK 21
- Maven 3.9+
- Docker y Docker Compose

## Ejecutar en local
1. Copia `.env.example` a `.env` y ajusta si es necesario.
2. Levanta PostgreSQL con Compose (opcional si ya tienes uno):
   - docker compose --env-file .env up -d db
3. Ejecuta la app:
   - mvn spring-boot:run

Endpoints:
- Salud: GET http://localhost:8080/health
- Swagger UI: http://localhost:8080/swagger-ui.html

## Ejecutar con Docker Compose
1. Copia `.env.example` a `.env` y ajusta variables.
2. Construye y levanta servicios:
   - docker compose --env-file .env up --build -d
3. Verifica:
   - http://localhost:${APP_PORT}/health
   - http://localhost:${APP_PORT}/swagger-ui.html

## Estructura propuesta
- src/main/java/una/ac/cr/Lab1Progra3
  - entity, repository, service, controller, dto, exception, config
- docs/
  - normalization.md (proceso 1FN-3FN)
  - erd.png (diagrama ER)
  - schema.sql (DDL final)

## Normalización y Modelo de Datos
Agrega en `docs/normalization.md` el análisis de las hojas Excel, entidades, claves, dependencias, y justificación hasta 3FN. Exporta el esquema final a `docs/schema.sql` y el diagrama como `docs/erd.png`.

## Notas
- Hibernate está configurado con `ddl-auto=update` para desarrollo. Para producción, migra a herramientas como Flyway o Liquibase.
- El API no requiere autenticación según enunciado.
