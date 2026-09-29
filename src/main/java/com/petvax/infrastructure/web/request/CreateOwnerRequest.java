package com.petvax.infrastructure.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateOwnerRequest(

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        String email,

        String phone

) {
}