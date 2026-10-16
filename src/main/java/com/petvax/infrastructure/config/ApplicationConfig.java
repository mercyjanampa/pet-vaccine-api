package com.petvax.infrastructure.config;

import com.petvax.application.port.in.*;
import com.petvax.application.port.out.*;
import com.petvax.application.service.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.petvax.application.service.AuthService;
import com.petvax.application.factory.NotificationStrategyFactory;
import java.util.List;

@Configuration
public class ApplicationConfig {

    // =========================
    // VACCINATION
    // =========================

    @Bean
    public RegisterVaccinationUseCase registerVaccinationUseCase(
            LoadPetPort loadPetPort,
            LoadVaccinePort loadVaccinePort,
            LoadUserPort loadUserPort,
            SaveVaccinationPort saveVaccinationPort,
            CheckVaccinationExistsPort checkVaccinationExistsPort
    ) {
        return new RegisterVaccinationService(
                loadPetPort,
                loadVaccinePort,
                loadUserPort,
                saveVaccinationPort,
                checkVaccinationExistsPort
        );
    }

    @Bean
    public ListVaccinationsUseCase listVaccinationsUseCase(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadUserPort loadUserPort
    ) {
        return new ListVaccinationsService(
                loadVaccinationsPort,
                loadUserPort
        );
    }

    @Bean
    public GetVaccinationUseCase getVaccinationUseCase(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort
    ) {
        return new GetVaccinationService(
                loadVaccinationsPort,
                loadPetPort,
                loadUserPort
        );
    }

    @Bean
    public ListVaccinationsByPetUseCase listVaccinationsByPetUseCase(
            LoadPetPort loadPetPort,
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        return new ListVaccinationsByPetService(
                loadPetPort,
                loadVaccinationsPort
        );
    }

    @Bean
    public ListExpiredVaccinationsUseCase listExpiredVaccinationsUseCase(
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        return new ListExpiredVaccinationsService(
                loadVaccinationsPort
        );
    }

    @Bean
    public UpdateVaccinationUseCase updateVaccinationUseCase(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadPetPort loadPetPort,
            LoadVaccinePort loadVaccinePort,
            SaveVaccinationPort saveVaccinationPort
    ) {
        return new UpdateVaccinationService(
                loadVaccinationsPort,
                loadPetPort,
                loadVaccinePort,
                saveVaccinationPort
        );
    }

    @Bean
    public DeleteVaccinationUseCase deleteVaccinationUseCase(
            LoadVaccinationsPort loadVaccinationsPort,
            DeleteVaccinationPort deleteVaccinationPort
    ) {
        return new DeleteVaccinationService(
                loadVaccinationsPort,
                deleteVaccinationPort
        );
    }

    // =========================
    // OWNER
    // =========================

    @Bean
    public CreateOwnerUseCase createOwnerUseCase(
            SaveOwnerPort saveOwnerPort
    ) {
        return new CreateOwnerService(saveOwnerPort);
    }

    @Bean
    public ListOwnersUseCase listOwnersUseCase(
            LoadOwnerPort loadOwnerPort
    ) {
        return new ListOwnersService(loadOwnerPort);
    }

    @Bean
    public GetOwnerUseCase getOwnerUseCase(
            LoadOwnerPort loadOwnerPort
    ) {
        return new GetOwnerService(loadOwnerPort);
    }

    @Bean
    public UpdateOwnerUseCase updateOwnerUseCase(
            LoadOwnerPort loadOwnerPort,
            SaveOwnerPort saveOwnerPort
    ) {
        return new UpdateOwnerService(
                loadOwnerPort,
                saveOwnerPort
        );
    }

    @Bean
    public DeleteOwnerUseCase deleteOwnerUseCase(
            LoadOwnerPort loadOwnerPort,
            DeleteOwnerPort deleteOwnerPort
    ) {
        return new DeleteOwnerService(
                loadOwnerPort,
                deleteOwnerPort
        );
    }

    // =========================
    // PET
    // =========================

    @Bean
    public CreatePetUseCase createPetUseCase(
            LoadOwnerPort loadOwnerPort,
            LoadUserPort loadUserPort,
            SavePetPort savePetPort
    ) {
        return new CreatePetService(
                loadOwnerPort,
                loadUserPort,
                savePetPort
        );
    }

    @Bean
    public ListPetsUseCase listPetsUseCase(
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort
    ) {
        return new ListPetsService(
                loadPetPort,
                loadUserPort
        );
    }

    @Bean
    public GetPetUseCase getPetUseCase(
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort
    ) {
        return new GetPetService(
                loadPetPort,
                loadUserPort
        );
    }

    @Bean
    public UpdatePetUseCase updatePetUseCase(
            LoadPetPort loadPetPort,
            LoadOwnerPort loadOwnerPort,
            SavePetPort savePetPort
    ) {
        return new UpdatePetService(
                loadPetPort,
                loadOwnerPort,
                savePetPort
        );
    }

    @Bean
    public DeletePetUseCase deletePetUseCase(
            LoadPetPort loadPetPort,
            DeletePetPort deletePetPort
    ) {
        return new DeletePetService(
                loadPetPort,
                deletePetPort
        );
    }

    // =========================
    // VACCINE
    // =========================

    @Bean
    public CreateVaccineUseCase createVaccineUseCase(
            SaveVaccinePort saveVaccinePort
    ) {
        return new CreateVaccineService(saveVaccinePort);
    }

    @Bean
    public ListVaccinesUseCase listVaccinesUseCase(
            LoadVaccinePort loadVaccinePort
    ) {
        return new ListVaccinesService(loadVaccinePort);
    }

    @Bean
    public GetVaccineUseCase getVaccineUseCase(
            LoadVaccinePort loadVaccinePort
    ) {
        return new GetVaccineService(loadVaccinePort);
    }

    @Bean
    public UpdateVaccineUseCase updateVaccineUseCase(
            LoadVaccinePort loadVaccinePort,
            SaveVaccinePort saveVaccinePort
    ) {
        return new UpdateVaccineService(
                loadVaccinePort,
                saveVaccinePort
        );
    }

    @Bean
    public DeleteVaccineUseCase deleteVaccineUseCase(
            LoadVaccinePort loadVaccinePort,
            DeleteVaccinePort deleteVaccinePort
    ) {
        return new DeleteVaccineService(
                loadVaccinePort,
                deleteVaccinePort
        );
    }

    @Bean
    public ListVaccinesBySpeciesUseCase listVaccinesBySpeciesUseCase(
            LoadVaccinePort loadVaccinePort
    ) {
        return new ListVaccinesBySpeciesService(
                loadVaccinePort
        );
    }
    @Bean
    public AuthService authService(
            SaveUserPort saveUserPort,
            LoadUserPort loadUserPort,
            CheckUserExistsPort checkUserExistsPort,
            SaveOwnerPort saveOwnerPort,
            PasswordHasherPort passwordHasherPort,
            AuthenticatorPort authenticatorPort,
            TokenIssuerPort tokenIssuerPort
    ) {
        return new AuthService(
                saveUserPort,
                loadUserPort,
                checkUserExistsPort,
                saveOwnerPort,
                passwordHasherPort,
                authenticatorPort,
                tokenIssuerPort
        );
    }
    @Bean
    public GetMyPetsUseCase getMyPetsUseCase(
            LoadUserPort loadUserPort,
            LoadPetPort loadPetPort
    ) {
        return new GetMyPetsService(
                loadUserPort,
                loadPetPort
        );
    }
    @Bean
    public GetMyVaccinationsUseCase getMyVaccinationsUseCase(
            LoadUserPort loadUserPort,
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        return new GetMyVaccinationsService(
                loadUserPort,
                loadVaccinationsPort
        );
    }
    @Bean
    public CreatePetForOwnerUseCase createPetForOwnerUseCase(
            LoadOwnerPort loadOwnerPort,
            SavePetPort savePetPort
    ) {
        return new CreatePetForOwnerService(
                loadOwnerPort,
                savePetPort
        );
    }
    @Bean
    public GetRecommendedVaccinesUseCase getRecommendedVaccinesUseCase(
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort,
            LoadVaccinePort loadVaccinePort
    ) {
        return new GetRecommendedVaccinesService(
                loadPetPort,
                loadUserPort,
                loadVaccinePort
        );
    }
    @Bean
    public NotificationStrategyFactory notificationStrategyFactory(
            List<NotificationStrategy> strategies
    ) {
        return new NotificationStrategyFactory(strategies);
    }
    @Bean
    public SendVaccinationReminderUseCase sendVaccinationReminderUseCase(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort,
            NotificationStrategyFactory notificationStrategyFactory
    ) {
        return new SendVaccinationReminderService(
                loadVaccinationsPort,
                loadPetPort,
                loadUserPort,
                notificationStrategyFactory
        );
    }
}