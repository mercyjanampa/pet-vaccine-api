package com.petvax.application.command;

public record CreateOwnerCommand(
        String name,
        String email,
        String phone
) {
}