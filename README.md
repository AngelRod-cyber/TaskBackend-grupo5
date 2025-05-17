# Task Manager Backend

Este proyecto es el backend del sistema de gestión de tareas, construido con Spring Boot. Proporciona una API REST segura para manejar autenticación de usuarios y operaciones CRUD sobre tareas.

## Tecnologías Utilizadas

* Java 17
* Spring Boot
* Spring Security + JWT
* Spring Data JPA
* Swagger / OpenAPI
* PostgreSQL (o base de datos relacional compatible)
* Docker / Docker Compose

## Estructura del Proyecto

```
src/main/java/com/kata/tareas/gestiondetareas
├── config               # Configuraciones de seguridad, JWT y Swagger
├── controllers          # Controladores REST (Auth y Task)
├── dto                  # Objetos de transferencia de datos
├── exception            # Manejo global de excepciones
├── model                # Entidades JPA (User y Task)
├── repository           # Interfaces JPA para acceso a datos
├── security             # Filtros y utilidades JWT
├── service              # Lógica de negocio
├── GestiondetareasApplication.java
```

## Endpoints Principales

### Autenticación

* `POST /auth/register` - Registro de usuarios
* `POST /auth/login` - Login y generación de token JWT

### Tareas

* `GET /tasks` - Obtener todas las tareas
* `POST /tasks` - Crear nueva tarea
* `PUT /tasks/{id}` - Actualizar tarea
* `DELETE /tasks/{id}` - Eliminar tarea

> Todos los endpoints de `/tasks` requieren autenticación JWT.

## Seguridad

Este proyecto utiliza JWT (JSON Web Token) para autenticar y autorizar a los usuarios. Asegúrate de enviar el token en el header `Authorization: Bearer <token>` para acceder a los recursos protegidos.

## Swagger

La documentación interactiva de la API está disponible en:

```
http://localhost:8080/swagger-ui/index.html
```

## Variables de Entorno

Configura los valores en `application.properties` o mediante variables de entorno:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=usuario
spring.datasource.password=contraseña
jwt.secret=secreto_personalizado
```

## Docker

Para levantar el backend junto con la base de datos:

```bash
docker-compose up --build
```

Esto usará `Dockerfile` y `docker-compose.yml` para iniciar la aplicación y PostgreSQL.

## Pruebas

El proyecto incluye pruebas unitarias para el controlador y el servicio de tareas en `src/test/java/...`.

```bash
./mvnw test
```

