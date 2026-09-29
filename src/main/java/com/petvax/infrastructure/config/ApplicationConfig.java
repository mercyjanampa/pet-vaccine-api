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
}