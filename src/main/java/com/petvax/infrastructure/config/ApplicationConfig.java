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

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterVaccinationUseCase registerVaccinationUseCase(
            LoadPetPort loadPetPort,
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

        // Spring conecta el caso de uso con el puerto que guarda vacunas
        return new CreateVaccineService(saveVaccinePort);
    }
}