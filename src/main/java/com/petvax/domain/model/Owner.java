package com.petvax.domain.model;

public class Owner {

    private Long id;
    private String name;
    private String email;
    private String phone;

    public Owner(Long id, String name, String email, String phone) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del dueño es obligatorio"
            );
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "El correo del dueño es obligatorio"
            );
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}