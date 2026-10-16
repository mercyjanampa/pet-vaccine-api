# Arquitectura actual de PetVax

## 1. Descripción general

PetVax se encuentra implementado actualmente como una aplicación monolítica desarrollada con Spring Boot.

La aplicación se ejecuta como un único proceso y utiliza una única base de datos MySQL. Sin embargo, internamente el proyecto mantiene una separación clara entre dominio, aplicación e infraestructura.

Este enfoque permite conservar una estructura ordenada y facilita una futura evolución hacia una arquitectura distribuida.

---

## 2. Estilo arquitectónico actual

Actualmente PetVax combina:

- Monolito modular.
- Arquitectura Hexagonal o Ports and Adapters.
- Principios SOLID.
- Autenticación y autorización con JWT.
- Persistencia mediante Spring Data JPA.
- Migraciones de base de datos mediante Flyway.
- Patrones Strategy y Factory para notificaciones.

La aplicación todavía se despliega como una sola unidad y comparte una misma base de datos.

---

## 3. Capas principales

### Domain

Contiene las reglas principales del negocio y los modelos:

- User.
- Owner.
- Pet.
- Vaccine.
- Vaccination.
- Role.
- Species.
- VaccinationStatus.
- VaccinationSource.
- NotificationType.

Esta capa no depende de Spring, JPA ni de componentes de infraestructura.

---

### Application

Contiene los casos de uso de PetVax.

Ejemplos:

- Registrar usuarios.
- Registrar mascotas.
- Consultar mascotas.
- Registrar vacunaciones.
- Consultar vacunaciones.
- Obtener vacunas recomendadas.
- Enviar recordatorios.
- Validar acceso a recursos según el propietario.

También contiene los puertos de entrada y salida utilizados para mantener desacoplada la lógica de negocio.

---

### Infrastructure

Contiene las tecnologías necesarias para comunicar la aplicación con el exterior.

Incluye:

- Controladores REST.
- Spring Security.
- JWT.
- Adaptadores de persistencia.
- Repositorios JPA.
- Entidades JPA.
- Configuración de Spring.
- Manejo global de excepciones.
- Estrategias de notificación.

---

## 4. Flujo general

El flujo principal de una petición es:

```text
Cliente
   |
   v
REST Controller
   |
   v
Input Port
   |
   v
Application Service
   |
   v
Domain
   |
   v
Output Port
   |
   v
Persistence Adapter
   |
   v
JpaRepository
   |
   v
MySQL