package com.petvax.infrastructure.persistence.adapter;

import com.petvax.application.port.out.SaveOwnerPort;
import com.petvax.domain.model.Owner;
import com.petvax.infrastructure.persistence.entity.OwnerEntity;
import com.petvax.infrastructure.persistence.mapper.OwnerMapper;
import com.petvax.infrastructure.persistence.repository.OwnerJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class OwnerPersistenceAdapter implements SaveOwnerPort {

    private final OwnerJpaRepository ownerRepository;

    public OwnerPersistenceAdapter(OwnerJpaRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner saveOwner(Owner owner) {

        OwnerEntity entity = OwnerMapper.toEntity(owner);

        OwnerEntity savedEntity = ownerRepository.save(entity);

        return OwnerMapper.toDomain(savedEntity);
    }
}