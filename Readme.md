# Proyecto Web con Spring Boot, Java 25

## Descripción

Este proyecto es una aplicación backend construida sobre **Java 25** y **Spring Boot**, La arquitectura combina persistencia y almacenamiento en caché distribuido.

## Tecnologías utilizadas

- **Java 25** — última versión LTS del lenguaje, con soporte para las mejoras más recientes de rendimiento y sintaxis.
- **Spring Boot** — framework base para la configuración, inyección de dependencias y arranque de la aplicación.
- **JPA** — controlador para el acceso a bases de datos relacionales, usando un ORM y postgresql.
- **Redis** — almacenamiento en caché distribuido para mejorar el rendimiento y reducir la carga sobre las bases de datos principales.
- **Docker** — contenedores para manejar las diferentes tecnologías utilizadas.

## Arquitectura general

La aplicación sigue un enfoque **persitencia y almacenamiento en cache**, usando redis como herramienta principal para cache:

1. **Capa de acceso a datos**: se combinan dos motores de persistencia según el tipo de dato:
    - **JPA** para datos relacionales y transaccionales.
2. **Capa de caché**: Redis se utiliza para almacenar resultados frecuentes y reducir la latencia en consultas repetitivas.
3. **Capa web **: Spring Boot expone los servicios APIRest, permitiendo manejar solicitudes.

## Requisitos previos

- Java 25 (JDK)
- Maven o Gradle
- Instancia de Redis
- Base de datos relacional compatible con JPA (PostgreSQL, MySQL, etc.)

## Configuración

Las credenciales y parámetros de conexión (hosts de Redis y la base de datos relacional) deben definirse en el archivo de configuración de la aplicación (`application.yml` o `application.properties`), o mediante variables de entorno según el entorno de despliegue (desarrollo, pruebas, producción).

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
 │   │   └── com/mvargas/redis_example
 │   │       ├── configs/        # Configuración Redis
 │   │       ├── controllers/    # Endpoints ApiRest
 │   │       ├── services/       # Lógica de negocio
 │   │       ├── repositories/   # Repositorios (JPA)
 │   │       └── models/         # entidades, datos de entrada y salida
 │   │
 │   └── resources/
 │       └── application.yml
 └── test/
 │
 └── ../db/
        └── sql/     #scripts para la db
```

## Notas adicionales

Este README es una plantilla base; se recomienda completar cada sección con los detalles específicos del proyecto (nombre real, endpoints disponibles, variables de entorno exactas y diagramas de arquitectura) a medida que el desarrollo avance.