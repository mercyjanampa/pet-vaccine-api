# PetVax API

API REST para la gestión y seguimiento de vacunación de mascotas.

PetVax permite registrar usuarios, propietarios, mascotas, vacunas y vacunaciones, aplicando control de acceso por usuario, seguridad con JWT, arquitectura hexagonal, principios SOLID y migraciones de base de datos con Flyway.

---

## Tecnologías

- Java 21
- Spring Boot
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Flyway
- Maven
- JUnit 5
- Mockito
- ArchUnit
- Swagger / OpenAPI

---

## Funcionalidades

### Autenticación y seguridad

- Registro de usuarios.
- Inicio de sesión.
- Generación de JWT.
- Contraseñas protegidas con BCrypt.
- Roles `USER` y `ADMIN`.
- Protección de endpoints según rol.
- Control de acceso por propietario.
- Respuesta `403 Forbidden` cuando un usuario intenta acceder a recursos ajenos.

### Propietarios

- Registrar propietarios.
- Consultar propietarios.
- Actualizar propietarios.
- Eliminar propietarios.

Las operaciones de administración de propietarios están restringidas al rol `ADMIN`.

### Mascotas

- Registrar mascotas.
- Consultar mascotas.
- Consultar una mascota por ID.
- Consultar las mascotas del usuario autenticado.
- Actualizar mascotas.
- Eliminar mascotas.
- Controlar que un usuario solo gestione sus propias mascotas.

### Vacunas

- Crear vacunas del catálogo.
- Consultar vacunas.
- Consultar vacunas por especie.
- Actualizar vacunas.
- Eliminar vacunas.
- Obtener recomendaciones según la especie de la mascota.

La gestión del catálogo está reservada al rol `ADMIN`.

### Vacunaciones

- Registrar vacunaciones del catálogo.
- Registrar vacunas aplicadas externamente.
- Consultar vacunaciones.
- Consultar vacunaciones por mascota.
- Consultar vacunaciones del usuario autenticado.
- Consultar vacunaciones vencidas.
- Actualizar vacunaciones.
- Eliminar vacunaciones.
- Evitar registros duplicados para la misma mascota, vacuna y fecha.
- Validar compatibilidad entre vacuna y especie.
- Calcular el estado de vacunación.
- Mostrar una acción recomendada según el estado.

Estados disponibles:

```text
PENDING
UPCOMING
UP_TO_DATE
EXPIRED