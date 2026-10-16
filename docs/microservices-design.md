# Diseño de Microservicios para PetVax

## 1. Objetivo

Este documento describe una propuesta de evolución de PetVax desde su arquitectura monolítica modular actual hacia una arquitectura basada en microservicios.

La separación física todavía no se implementa. En esta etapa se definen responsabilidades, límites, propiedad de datos, contratos de comunicación y posibles riesgos.

---

## 2. Criterio de separación

Los servicios se proponen según capacidades de negocio y no según tablas individuales.

Se busca:

- Alta cohesión dentro de cada servicio.
- Bajo acoplamiento entre servicios.
- Propiedad clara de datos.
- Contratos explícitos.
- Independencia de despliegue.

---

## 3. Servicios propuestos

### 3.1 Auth Service

Responsabilidad principal:

Gestionar identidad y autenticación.

Funciones:

- Registro de usuarios.
- Login.
- Generación de JWT.
- Roles USER y ADMIN.
- Validación de credenciales.

Datos propios:

```text
users