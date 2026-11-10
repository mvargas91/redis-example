# Proyecto Reactivo con Spring Boot, Java 25

## Descripción

Este proyecto es una aplicación backend reactiva construida sobre **Java 25** y **Spring Boot**, diseñada para ofrecer alto rendimiento y escalabilidad mediante programación no bloqueante. La arquitectura combina persistencia reactiva, mensajería por eventos, autenticación basada en tokens y almacenamiento en caché distribuido.

## Tecnologías utilizadas

- **Java 25** — última versión LTS del lenguaje, con soporte para las mejoras más recientes de rendimiento y sintaxis.
- **Spring Boot** — framework base para la configuración, inyección de dependencias y arranque de la aplicación.
- **Spring WebFlux (Reactive Flux)** — programación reactiva no bloqueante para exponer y consumir endpoints HTTP, basada en Project Reactor (`Mono` / `Flux`).
- **R2DBC** — controlador reactivo para el acceso a bases de datos relacionales, evitando el bloqueo de hilos en operaciones de I/O.
- **MongoDB (Reactive)** — base de datos NoSQL utilizada para el almacenamiento de datos no estructurados o documentos, mediante driver reactivo.
- **Redis** — almacenamiento en caché distribuido para mejorar el rendimiento y reducir la carga sobre las bases de datos principales.
- **Apache Kafka** — sistema de mensajería basado en eventos, utilizado para la comunicación asíncrona entre servicios y el procesamiento de streams de datos.
- **JWT (JSON Web Token)** — mecanismo de autenticación y autorización sin estado (stateless), utilizado para proteger los endpoints de la API.
- **Docker** — contenedores para manejar las diferentes tecnologías utilizadas.

## Arquitectura general

La aplicación sigue un enfoque **reactivo de extremo a extremo**, evitando el uso de hilos bloqueantes en toda la cadena de procesamiento:

1. **Capa de seguridad**: los endpoints están protegidos mediante tokens JWT, validados en cada solicitud sin necesidad de mantener sesión en el servidor.
2. **Capa de acceso a datos**: se combinan dos motores de persistencia según el tipo de dato:
    - **R2DBC** para datos relacionales y transaccionales.
    - **MongoDB reactivo** para datos documentales o de esquema flexible.
3. **Capa de caché**: Redis se utiliza para almacenar resultados frecuentes y reducir la latencia en consultas repetitivas.
4. **Capa de mensajería**: Kafka gestiona la publicación y consumo de eventos entre los distintos componentes o microservicios del sistema.
5. **Capa web reactiva**: Spring WebFlux expone los servicios como flujos reactivos (`Mono`/`Flux`), permitiendo manejar múltiples solicitudes concurrentes con un número reducido de hilos.

## Requisitos previos

- Java 25 (JDK)
- Maven o Gradle
- Instancia de MongoDB
- Instancia de Redis
- Cluster o instancia de Kafka
- Base de datos relacional compatible con R2DBC (PostgreSQL, MySQL, etc.)

## Configuración

Las credenciales y parámetros de conexión (JWT secret, hosts de Redis, Mongo, Kafka y la base de datos relacional) deben definirse en el archivo de configuración de la aplicación (`application.yml` o `application.properties`), o mediante variables de entorno según el entorno de despliegue (desarrollo, pruebas, producción).

## Ejecución

```bash
# Compilar el proyecto
./mvnw clean package

# Ejecutar la aplicación
./mvnw spring-boot:run
```

## Estructura sugerida del proyecto

```
src/
 ├── main/
 │   ├── java/
 │   │   └── com/proyecto/
 │   │       ├── config/        # Configuración de seguridad, JWT, Redis, Kafka, R2DBC
 │   │       ├── controller/    # Endpoints reactivos (WebFlux)
 │   │       ├── service/       # Lógica de negocio
 │   │       ├── repository/    # Repositorios reactivos (R2DBC / Mongo)
 │   │       ├── messaging/     # Productores y consumidores Kafka
 │   │       └── security/      # Filtros y validación de JWT
 │   └── resources/
 │       └── application.yml
 └── test/
```

## Notas adicionales

Este README es una plantilla base; se recomienda completar cada sección con los detalles específicos del proyecto (nombre real, endpoints disponibles, variables de entorno exactas y diagramas de arquitectura) a medida que el desarrollo avance.