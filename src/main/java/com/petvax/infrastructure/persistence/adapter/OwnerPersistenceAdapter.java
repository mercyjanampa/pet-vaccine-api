package com.petvax.infrastructure.persistence.adapter;

import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.application.port.out.LoadOwnersPort;
import com.petvax.application.port.out.SaveOwnerPort;
import com.petvax.domain.model.Owner;
import com.petvax.infrastructure.persistence.entity.OwnerEntity;
import com.petvax.infrastructure.persistence.mapper.OwnerMapper;
import com.petvax.infrastructure.persistence.repository.OwnerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OwnerPersistenceAdapter
        implements SaveOwnerPort, LoadOwnerPort, LoadOwnersPort {

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

    @Override
    public Optional<Owner> findOwnerById(Long id) {

        return ownerRepository.findById(id)
                .map(OwnerMapper::toDomain);
    }

    @Override
    public List<Owner> findAllOwners() {

        return ownerRepository.findAll()
                .stream()
                .map(OwnerMapper::toDomain)
                .toList();
    }
}