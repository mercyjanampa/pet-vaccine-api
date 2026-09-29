package com.petvax.infrastructure.web.response;

public record OwnerResponse(
        Long id,
        String name,
        String email,
        String phone
) {
}