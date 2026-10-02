package com.petvax.application.service;

import com.petvax.application.command.RegisterVaccinationCommand;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Species;
import com.petvax.domain.model.Vaccination;
import com.petvax.domain.model.Vaccine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.petvax.application.port.out.CheckVaccinationExistsPort;

@ExtendWith(MockitoExtension.class)
class RegisterVaccinationServiceTest {

    @Mock
    private LoadPetPort loadPetPort;

    @Mock
    private LoadVaccinePort loadVaccinePort;

    @Mock
    private SaveVaccinationPort saveVaccinationPort;

    @Mock
    private CheckVaccinationExistsPort checkVaccinationExistsPort;

    @InjectMocks
    private RegisterVaccinationService service;

    @Test
    void shouldRegisterVaccinationWhenDataIsValid() {

        Pet pet = new Pet(
                1L,
                "Chetos",
                Species.DOG,
                "Labrador",
                LocalDate.of(2023, 5, 10),
                "F",
                1L
        );

        Vaccine vaccine = new Vaccine(
                1L,
                "Vacuna de prueba",
                Species.DOG,
                "Vacuna para pruebas",
                12
        );

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Registro correcto"
                );

        Vaccination savedVaccination = new Vaccination(
                10L,
                1L,
                1L,
                command.applicationDate(),
                command.nextDoseDate(),
                command.notes()
        );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

        when(saveVaccinationPort.save(any(Vaccination.class)))
                .thenReturn(savedVaccination);

        Vaccination result = service.register(command);

        assertEquals(10L, result.getId());
        assertEquals(1L, result.getPetId());
        assertEquals(1L, result.getVaccineId());

        verify(saveVaccinationPort, times(1))
                .save(any(Vaccination.class));
    }

    @Test
    void shouldFailWhenPetDoesNotExist() {

        when(loadPetPort.findPetById(99L))
                .thenReturn(Optional.empty());

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        99L,
                        1L,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Prueba"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.register(command)
                );

        assertEquals("La mascota no existe", exception.getMessage());

        verify(saveVaccinationPort, never())
                .save(any());
    }

    @Test
    void shouldFailWhenVaccineDoesNotExist() {

        Pet pet = new Pet(
                1L,
                "Chetos",
                Species.DOG,
                "Labrador",
                LocalDate.of(2023, 5, 10),
                "F",
                1L
        );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadVaccinePort.findVaccineById(99L))
                .thenReturn(Optional.empty());

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        99L,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Prueba"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.register(command)
                );

        assertEquals("La vacuna no existe", exception.getMessage());

        verify(saveVaccinationPort, never())
                .save(any());
    }

    @Test
    void shouldFailWhenVaccineDoesNotMatchPetSpecies() {

        Pet pet = new Pet(
                1L,
                "Chetos",
                Species.DOG,
                "Labrador",
                LocalDate.of(2023, 5, 10),
                "F",
                1L
        );

        Vaccine vaccine = new Vaccine(
                1L,
                "Vacuna para gato",
                Species.CAT,
                "Vacuna incompatible con la mascota",
                12
        );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Prueba de especie"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.register(command)
                );

        assertEquals(
                "La vacuna no corresponde a la especie de la mascota",
                exception.getMessage()
        );

        verify(saveVaccinationPort, never())
                .save(any());
    }

    @Test
    void shouldFailWhenNextDoseIsBeforeApplicationDate() {

        Pet pet = new Pet(
                1L,
                "Chetos",
                Species.DOG,
                "Labrador",
                LocalDate.of(2023, 5, 10),
                "F",
                1L
        );

        Vaccine vaccine = new Vaccine(
                1L,
                "Vacuna de prueba",
                Species.DOG,
                "Vacuna compatible",
                12
        );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalDate.now().minusDays(1),
                        "Fecha incorrecta"
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.register(command)
        );

        verify(saveVaccinationPort, never())
                .save(any());
    }
    @Test
    void shouldFailWhenVaccinationAlreadyExists() {

        Pet pet = new Pet(
                1L,
                "Chetos",
                Species.DOG,
                "Labrador",
                LocalDate.of(2023, 5, 10),
                "F",
                1L
        );

        Vaccine vaccine = new Vaccine(
                1L,
                "Vacuna de prueba",
                Species.DOG,
                "Vacuna para pruebas",
                12
        );

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Registro duplicado"
                );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

        // Simulo que ya existe el mismo registro
        when(checkVaccinationExistsPort
                .existsByPetIdAndVaccineIdAndApplicationDate(
                        1L,
                        1L,
                        command.applicationDate()
                ))
                .thenReturn(true);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.register(command)
                );

        assertEquals(
                "Ya existe una vacunación registrada para la misma mascota, vacuna y fecha",
                exception.getMessage()
        );

        // Si está duplicada, no debe guardarse
        verify(saveVaccinationPort, never())
                .save(any(Vaccination.class));
    }
}