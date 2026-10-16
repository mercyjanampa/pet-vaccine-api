package com.petvax.application.service;

import com.petvax.application.command.RegisterVaccinationCommand;
import com.petvax.application.port.out.CheckVaccinationExistsPort;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.Species;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccination;
import com.petvax.domain.model.VaccinationSource;
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

@ExtendWith(MockitoExtension.class)
class RegisterVaccinationServiceTest {

    private static final String USER_EMAIL = "mercy@gmail.com";
    private static final String ADMIN_EMAIL = "admin@petvax.com";

    @Mock
    private LoadPetPort loadPetPort;

    @Mock
    private LoadVaccinePort loadVaccinePort;

    @Mock
    private LoadUserPort loadUserPort;

    @Mock
    private SaveVaccinationPort saveVaccinationPort;

    @Mock
    private CheckVaccinationExistsPort checkVaccinationExistsPort;

    @InjectMocks
    private RegisterVaccinationService service;

    @Test
    void shouldRegisterCatalogVaccinationWhenDataIsValid() {

        Pet pet = createDogPet(1L, 1L);
        Vaccine vaccine = createDogVaccine();

        User user = new User(
                1L,
                "Mercy",
                USER_EMAIL,
                "passwordHash",
                Role.USER,
                1L
        );

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        null,
                        VaccinationSource.CATALOG,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Registro correcto"
                );

        Vaccination savedVaccination =
                new Vaccination(
                        10L,
                        1L,
                        1L,
                        null,
                        VaccinationSource.CATALOG,
                        command.applicationDate(),
                        command.nextDoseDate(),
                        command.notes()
                );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadUserPort.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(user));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

        when(saveVaccinationPort.save(any(Vaccination.class)))
                .thenReturn(savedVaccination);

        Vaccination result =
                service.register(command, USER_EMAIL);

        assertEquals(10L, result.getId());
        assertEquals(1L, result.getPetId());
        assertEquals(1L, result.getVaccineId());
        assertEquals(VaccinationSource.CATALOG, result.getSource());
        assertNull(result.getCustomVaccineName());

        verify(saveVaccinationPort, times(1))
                .save(any(Vaccination.class));
    }

    @Test
    void shouldRegisterExternalVaccination() {

        Pet pet = createDogPet(1L, 1L);

        User user = new User(
                1L,
                "Mercy",
                USER_EMAIL,
                "passwordHash",
                Role.USER,
                1L
        );

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        null,
                        "Vacuna aplicada en otra veterinaria",
                        VaccinationSource.EXTERNAL,
                        LocalDate.now(),
                        null,
                        "Registro informado por la propietaria"
                );

        Vaccination savedVaccination =
                new Vaccination(
                        20L,
                        1L,
                        null,
                        "Vacuna aplicada en otra veterinaria",
                        VaccinationSource.EXTERNAL,
                        command.applicationDate(),
                        null,
                        command.notes()
                );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadUserPort.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(user));

        when(saveVaccinationPort.save(any(Vaccination.class)))
                .thenReturn(savedVaccination);

        Vaccination result =
                service.register(command, USER_EMAIL);

        assertEquals(20L, result.getId());
        assertNull(result.getVaccineId());

        assertEquals(
                "Vacuna aplicada en otra veterinaria",
                result.getCustomVaccineName()
        );

        assertEquals(
                VaccinationSource.EXTERNAL,
                result.getSource()
        );

        verify(loadVaccinePort, never())
                .findVaccineById(any());

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
                        null,
                        VaccinationSource.CATALOG,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Prueba"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.register(
                                command,
                                USER_EMAIL
                        )
                );

        assertEquals(
                "La mascota no existe",
                exception.getMessage()
        );

        verify(saveVaccinationPort, never())
                .save(any());
    }

    @Test
    void shouldFailWhenUserTriesToVaccinateAnotherOwnersPet() {

        // La mascota pertenece al owner 2
        Pet pet = createDogPet(1L, 2L);

        // Mercy pertenece al owner 1
        User user = new User(
                1L,
                "Mercy",
                USER_EMAIL,
                "passwordHash",
                Role.USER,
                1L
        );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadUserPort.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(user));

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        null,
                        VaccinationSource.CATALOG,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Intento sobre mascota ajena"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.register(
                                command,
                                USER_EMAIL
                        )
                );

        assertEquals(
                "No tienes acceso para registrar vacunaciones en esta mascota",
                exception.getMessage()
        );

        verify(loadVaccinePort, never())
                .findVaccineById(any());

        verify(saveVaccinationPort, never())
                .save(any());
    }

    @Test
    void shouldAllowAdminToVaccinateAnyPet() {

        Pet pet = createDogPet(1L, 55L);
        Vaccine vaccine = createDogVaccine();

        User admin = new User(
                100L,
                "Administrador",
                ADMIN_EMAIL,
                "passwordHash",
                Role.ADMIN,
                null
        );

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        null,
                        VaccinationSource.CATALOG,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Registrada por administrador"
                );

        Vaccination savedVaccination =
                new Vaccination(
                        30L,
                        1L,
                        1L,
                        null,
                        VaccinationSource.CATALOG,
                        command.applicationDate(),
                        command.nextDoseDate(),
                        command.notes()
                );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadUserPort.findByEmail(ADMIN_EMAIL))
                .thenReturn(Optional.of(admin));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

        when(saveVaccinationPort.save(any(Vaccination.class)))
                .thenReturn(savedVaccination);

        Vaccination result =
                service.register(command, ADMIN_EMAIL);

        assertEquals(30L, result.getId());

        verify(saveVaccinationPort, times(1))
                .save(any(Vaccination.class));
    }

    @Test
    void shouldFailWhenVaccineDoesNotExist() {

        Pet pet = createDogPet(1L, 1L);

        User user = new User(
                1L,
                "Mercy",
                USER_EMAIL,
                "passwordHash",
                Role.USER,
                1L
        );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadUserPort.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(user));

        when(loadVaccinePort.findVaccineById(99L))
                .thenReturn(Optional.empty());

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        99L,
                        null,
                        VaccinationSource.CATALOG,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Prueba"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.register(
                                command,
                                USER_EMAIL
                        )
                );

        assertEquals(
                "La vacuna no existe",
                exception.getMessage()
        );

        verify(saveVaccinationPort, never())
                .save(any());
    }

    @Test
    void shouldFailWhenVaccineDoesNotMatchPetSpecies() {

        Pet pet = createDogPet(1L, 1L);

        Vaccine vaccine = new Vaccine(
                1L,
                "Vacuna para gato",
                Species.CAT,
                "Vacuna incompatible con la mascota",
                12
        );

        User user = new User(
                1L,
                "Mercy",
                USER_EMAIL,
                "passwordHash",
                Role.USER,
                1L
        );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadUserPort.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(user));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        null,
                        VaccinationSource.CATALOG,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Prueba de especie"
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.register(
                                command,
                                USER_EMAIL
                        )
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

        Pet pet = createDogPet(1L, 1L);
        Vaccine vaccine = createDogVaccine();

        User user = new User(
                1L,
                "Mercy",
                USER_EMAIL,
                "passwordHash",
                Role.USER,
                1L
        );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadUserPort.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(user));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        null,
                        VaccinationSource.CATALOG,
                        LocalDate.now(),
                        LocalDate.now().minusDays(1),
                        "Fecha incorrecta"
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.register(
                        command,
                        USER_EMAIL
                )
        );

        verify(saveVaccinationPort, never())
                .save(any());
    }

    @Test
    void shouldFailWhenVaccinationAlreadyExists() {

        Pet pet = createDogPet(1L, 1L);
        Vaccine vaccine = createDogVaccine();

        User user = new User(
                1L,
                "Mercy",
                USER_EMAIL,
                "passwordHash",
                Role.USER,
                1L
        );

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        1L,
                        1L,
                        null,
                        VaccinationSource.CATALOG,
                        LocalDate.now(),
                        LocalDate.now().plusMonths(12),
                        "Registro duplicado"
                );

        when(loadPetPort.findPetById(1L))
                .thenReturn(Optional.of(pet));

        when(loadUserPort.findByEmail(USER_EMAIL))
                .thenReturn(Optional.of(user));

        when(loadVaccinePort.findVaccineById(1L))
                .thenReturn(Optional.of(vaccine));

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
                        () -> service.register(
                                command,
                                USER_EMAIL
                        )
                );

        assertEquals(
                "Ya existe una vacunación registrada para la misma mascota, vacuna y fecha",
                exception.getMessage()
        );

        verify(saveVaccinationPort, never())
                .save(any(Vaccination.class));
    }

    private Pet createDogPet(
            Long petId,
            Long ownerId
    ) {

        return new Pet(
                petId,
                "Chetos",
                Species.DOG,
                "Labrador",
                LocalDate.of(2023, 5, 10),
                "M",
                ownerId
        );
    }

    private Vaccine createDogVaccine() {

        return new Vaccine(
                1L,
                "Vacuna de prueba",
                Species.DOG,
                "Vacuna para pruebas",
                12
        );
    }
}