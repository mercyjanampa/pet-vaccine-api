package com.petvax.infrastructure.config;
import com.petvax.application.port.in.RegisterVaccinationUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.application.service.RegisterVaccinationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.petvax.application.port.in.CreateOwnerUseCase;
import com.petvax.application.port.out.SaveOwnerPort;
import com.petvax.application.service.CreateOwnerService;
import com.petvax.application.port.in.CreatePetUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.application.port.out.SavePetPort;
import com.petvax.application.service.CreatePetService;
import com.petvax.application.port.in.CreateVaccineUseCase;
import com.petvax.application.port.out.SaveVaccinePort;
import com.petvax.application.service.CreateVaccineService;
import com.petvax.application.port.in.ListOwnersUseCase;
import com.petvax.application.port.out.LoadOwnersPort;
import com.petvax.application.service.ListOwnersService;
import com.petvax.application.port.in.ListPetsUseCase;
import com.petvax.application.port.out.LoadPetsPort;
import com.petvax.application.service.ListPetsService;
import com.petvax.application.port.in.ListVaccinesUseCase;
import com.petvax.application.port.out.LoadVaccinesPort;
import com.petvax.application.service.ListVaccinesService;
import com.petvax.application.port.in.GetOwnerUseCase;
import com.petvax.application.port.in.UpdateOwnerUseCase;
import com.petvax.application.port.in.DeleteOwnerUseCase;
import com.petvax.application.port.out.DeleteOwnerPort;
import com.petvax.application.service.GetOwnerService;
import com.petvax.application.service.UpdateOwnerService;
import com.petvax.application.service.DeleteOwnerService;
import com.petvax.application.port.in.GetPetUseCase;
import com.petvax.application.port.in.UpdatePetUseCase;
import com.petvax.application.port.in.DeletePetUseCase;
import com.petvax.application.port.out.DeletePetPort;
import com.petvax.application.service.GetPetService;
import com.petvax.application.service.UpdatePetService;
import com.petvax.application.service.DeletePetService;
import org.springframework.beans.factory.annotation.Qualifier;
import com.petvax.application.port.in.GetVaccineUseCase;
import com.petvax.application.port.in.UpdateVaccineUseCase;
import com.petvax.application.port.in.DeleteVaccineUseCase;
import com.petvax.application.port.out.DeleteVaccinePort;
import com.petvax.application.service.GetVaccineService;
import com.petvax.application.service.UpdateVaccineService;
import com.petvax.application.service.DeleteVaccineService;
import com.petvax.application.port.in.ListVaccinesBySpeciesUseCase;
import com.petvax.application.port.out.LoadVaccinesBySpeciesPort;
import com.petvax.application.service.ListVaccinesBySpeciesService;
import com.petvax.application.port.in.GetVaccinationUseCase;
import com.petvax.application.port.in.ListExpiredVaccinationsUseCase;
import com.petvax.application.port.in.ListVaccinationsByPetUseCase;
import com.petvax.application.port.in.ListVaccinationsUseCase;

import com.petvax.application.port.out.LoadVaccinationsPort;

import com.petvax.application.service.GetVaccinationService;
import com.petvax.application.service.ListExpiredVaccinationsService;
import com.petvax.application.service.ListVaccinationsByPetService;
import com.petvax.application.service.ListVaccinationsService;
import com.petvax.application.port.in.UpdateVaccinationUseCase;
import com.petvax.application.port.in.DeleteVaccinationUseCase;

import com.petvax.application.port.out.DeleteVaccinationPort;

import com.petvax.application.service.UpdateVaccinationService;
import com.petvax.application.service.DeleteVaccinationService;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterVaccinationUseCase registerVaccinationUseCase(
            @Qualifier("vaccinationPersistenceAdapter")
            LoadPetPort loadPetPort,

            @Qualifier("vaccinationPersistenceAdapter")
            LoadVaccinePort loadVaccinePort,

            SaveVaccinationPort saveVaccinationPort
    ) {

        return new RegisterVaccinationService(
                loadPetPort,
                loadVaccinePort,
                saveVaccinationPort
        );
    }
    @Bean
    public CreateOwnerUseCase createOwnerUseCase(
            SaveOwnerPort saveOwnerPort
    ) {

        return new CreateOwnerService(saveOwnerPort);
    }
    @Bean
    public CreatePetUseCase createPetUseCase(
            LoadOwnerPort loadOwnerPort,
            SavePetPort savePetPort
    ) {

        return new CreatePetService(
                loadOwnerPort,
                savePetPort
        );
    }
    @Bean
    public CreateVaccineUseCase createVaccineUseCase(
            SaveVaccinePort saveVaccinePort
    ) {

        return new CreateVaccineService(saveVaccinePort);
    }
    @Bean
    public ListOwnersUseCase listOwnersUseCase(
            LoadOwnersPort loadOwnersPort
    ) {

        return new ListOwnersService(loadOwnersPort);
    }
    @Bean
    public ListPetsUseCase listPetsUseCase(
            LoadPetsPort loadPetsPort
    ) {

        return new ListPetsService(loadPetsPort);
    }
    @Bean
    public ListVaccinesUseCase listVaccinesUseCase(
            LoadVaccinesPort loadVaccinesPort
    ) {

        return new ListVaccinesService(loadVaccinesPort);
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
    @Bean
    public GetPetUseCase getPetUseCase(
            @Qualifier("petPersistenceAdapter")
            LoadPetPort loadPetPort
    ) {

        return new GetPetService(loadPetPort);
    }

    @Bean
    public UpdatePetUseCase updatePetUseCase(
            @Qualifier("petPersistenceAdapter")
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
            @Qualifier("petPersistenceAdapter")
            LoadPetPort loadPetPort,
            DeletePetPort deletePetPort
    ) {

        return new DeletePetService(
                loadPetPort,
                deletePetPort
        );
    }
    @Bean
    public GetVaccineUseCase getVaccineUseCase(
            @Qualifier("vaccinePersistenceAdapter")
            LoadVaccinePort loadVaccinePort
    ) {

        return new GetVaccineService(loadVaccinePort);
    }
    @Bean
    public UpdateVaccineUseCase updateVaccineUseCase(
            @Qualifier("vaccinePersistenceAdapter")
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
            @Qualifier("vaccinePersistenceAdapter")
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
            LoadVaccinesBySpeciesPort loadVaccinesBySpeciesPort
    ) {

        return new ListVaccinesBySpeciesService(
                loadVaccinesBySpeciesPort
        );
    }
    @Bean
    public ListVaccinationsUseCase listVaccinationsUseCase(
            LoadVaccinationsPort loadVaccinationsPort
    ) {

        return new ListVaccinationsService(loadVaccinationsPort);
    }

    @Bean
    public GetVaccinationUseCase getVaccinationUseCase(
            LoadVaccinationsPort loadVaccinationsPort
    ) {

        return new GetVaccinationService(loadVaccinationsPort);
    }

    @Bean
    public ListVaccinationsByPetUseCase listVaccinationsByPetUseCase(
            @Qualifier("vaccinationPersistenceAdapter")
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

            @Qualifier("vaccinationPersistenceAdapter")
            LoadPetPort loadPetPort,

            @Qualifier("vaccinationPersistenceAdapter")
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
}