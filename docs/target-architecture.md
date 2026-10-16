# Arquitectura objetivo de PetVax

## 1. Objetivo

La arquitectura objetivo de PetVax busca evolucionar el sistema actual hacia una arquitectura orientada a microservicios.

La intención no es dividir inmediatamente la aplicación, sino definir primero los límites funcionales, la propiedad de los datos y la comunicación entre servicios.

---

## 2. Servicios propuestos

PetVax podría separarse en los siguientes servicios:

### Auth Service

Responsable de:

- Registro de usuarios.
- Inicio de sesión.
- Gestión de roles.
- Generación de tokens JWT.
- Validación de identidad.

Datos principales:

```text
users