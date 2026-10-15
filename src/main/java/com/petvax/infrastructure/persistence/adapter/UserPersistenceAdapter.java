package com.petvax.infrastructure.persistence.adapter;

import com.petvax.application.port.out.CheckUserExistsPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.SaveUserPort;
import com.petvax.domain.model.User;
import com.petvax.infrastructure.persistence.mapper.UserMapper;
import com.petvax.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter
        implements LoadUserPort, SaveUserPort, CheckUserExistsPort {

    private final UserJpaRepository repository;

    public UserPersistenceAdapter(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var entity = UserMapper.toEntity(user);
        var saved = repository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}