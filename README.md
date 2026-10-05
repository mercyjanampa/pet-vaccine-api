# PetVax API

API REST para gestionar dueños, mascotas, vacunas y registros de vacunación.

El proyecto fue desarrollado con Spring Boot aplicando arquitectura hexagonal, persistencia con MySQL, pruebas automatizadas y seguridad con JWT.

## Funcionalidades

- Registrar dueños.
- Registrar mascotas.
- Registrar vacunas.
- Consultar vacunas por especie.
- Registrar vacunaciones.
- Consultar vacunaciones.
- Consultar historial de vacunaciones por mascota.
- Consultar vacunaciones vencidas.
- Actualizar registros.
- Eliminar registros.
- Evitar vacunaciones duplicadas para la misma mascota, vacuna y fecha.
- Validar reglas de negocio desde el dominio.
- Proteger endpoints con JWT y roles.

## Arquitectura

El proyecto mantiene separadas las responsabilidades principales:

```text
domain
application
infrastructure
web