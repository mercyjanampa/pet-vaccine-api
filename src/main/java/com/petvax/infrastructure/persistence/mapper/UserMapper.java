package com.petvax.infrastructure.persistence.mapper;

import com.petvax.domain.model.User;
import com.petvax.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    private UserMapper() {
    }

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getOwnerId()
        );
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getOwnerId()
        );
    }
}