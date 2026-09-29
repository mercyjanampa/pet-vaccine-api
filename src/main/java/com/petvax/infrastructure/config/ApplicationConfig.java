package com.petvax.infrastructure.config;

import com.petvax.application.port.in.RegisterVaccinationUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.application.service.RegisterVaccinationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterVaccinationUseCase registerVaccinationUseCase(
            LoadPetPort loadPetPort,
            LoadVaccinePort loadVaccinePort,
            SaveVaccinationPort saveVaccinationPort
    ) {

        // Spring conecta aquí nuestro caso de uso con los puertos necesarios
        return new RegisterVaccinationService(
                loadPetPort,
                loadVaccinePort,
                saveVaccinationPort
        );
    }
}