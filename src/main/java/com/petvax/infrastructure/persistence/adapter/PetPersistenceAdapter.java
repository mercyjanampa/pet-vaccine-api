package com.petvax.infrastructure.persistence.adapter;

import com.petvax.application.port.out.SavePetPort;
import com.petvax.domain.model.Pet;
import com.petvax.infrastructure.persistence.entity.OwnerEntity;
import com.petvax.infrastructure.persistence.entity.PetEntity;
import com.petvax.infrastructure.persistence.mapper.PetMapper;
import com.petvax.infrastructure.persistence.repository.OwnerJpaRepository;
import com.petvax.infrastructure.persistence.repository.PetJpaRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.DeletePetPort;
import com.petvax.domain.model.Pet;
import com.petvax.infrastructure.persistence.mapper.PetMapper;

import java.util.Optional;

@Component
public class PetPersistenceAdapter
        implements SavePetPort,
        LoadPetPort,
        DeletePetPort {

    private final PetJpaRepository petRepository;
    private final OwnerJpaRepository ownerRepository;

    public PetPersistenceAdapter(
            PetJpaRepository petRepository,
            OwnerJpaRepository ownerRepository
    ) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }
    @Override
    public Optional<Pet> findPetById(Long id) {

        return petRepository.findById(id)
                .map(PetMapper::toDomain);
    }
    @Override
    public Pet savePet(Pet pet) {

        OwnerEntity owner = ownerRepository.findById(pet.getOwnerId())
                .orElseThrow(() ->
                        new IllegalArgumentException("El dueño no existe")
                );

        PetEntity entity = new PetEntity(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getSex(),
                owner
        );

        PetEntity savedEntity = petRepository.save(entity);

        return PetMapper.toDomain(savedEntity);
    }
    @Override
    public List<Pet> findAllPets() {

        return petRepository.findAll()
                .stream()
                .map(PetMapper::toDomain)
                .toList();
    }
    @Override
    public void deletePetById(Long id) {

        petRepository.deleteById(id);
    }
}