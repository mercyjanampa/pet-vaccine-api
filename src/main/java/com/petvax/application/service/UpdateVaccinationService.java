package com.petvax.application.service;

import com.petvax.application.port.in.UpdateVaccinationUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Vaccination;
import com.petvax.domain.model.Vaccine;

import java.time.LocalDate;

public class UpdateVaccinationService
        implements UpdateVaccinationUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;
    private final LoadPetPort loadPetPort;
    private final LoadVaccinePort loadVaccinePort;
    private final SaveVaccinationPort saveVaccinationPort;

    public UpdateVaccinationService(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadPetPort loadPetPort,
            LoadVaccinePort loadVaccinePort,
            SaveVaccinationPort saveVaccinationPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
        this.loadPetPort = loadPetPort;
        this.loadVaccinePort = loadVaccinePort;
        this.saveVaccinationPort = saveVaccinationPort;
    }

    @Override
    public Vaccination update(
            Long id,
            Long petId,
            Long vaccineId,
            LocalDate applicationDate,
            LocalDate nextDoseDate,
            String notes
    ) {

        loadVaccinationsPort.findVaccinationById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La vacunación no existe"
                        )
                );

        Pet pet = loadPetPort.findPetById(petId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        Vaccine vaccine = loadVaccinePort.findVaccineById(vaccineId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La vacuna no existe"
                        )
                );

        if (pet.getSpecies() != vaccine.getSpecies()) {
            throw new IllegalArgumentException(
                    "La vacuna no corresponde a la especie de la mascota"
            );
        }

        Vaccination vaccination = new Vaccination(
                id,
                petId,
                vaccineId,
                applicationDate,
                nextDoseDate,
                notes
        );

        return saveVaccinationPort.save(vaccination);
    }
}