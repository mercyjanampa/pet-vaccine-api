package com.petvax.infrastructure.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecretKey jwtKey(
            @Value("${jwt.secret}") String secret
    ) {
        return new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
    }

    @Bean
    public JwtEncoder jwtEncoder(
            SecretKey key
    ) {
        return new NimbusJwtEncoder(
                new ImmutableSecret<>(key)
        );
    }

    @Bean
    public JwtDecoder jwtDecoder(
            SecretKey key
    ) {
        return NimbusJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService users,
            PasswordEncoder encoder
    ) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(users);

        provider.setPasswordEncoder(encoder);

        return new ProviderManager(provider);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter roles =
                new JwtGrantedAuthoritiesConverter();

        // El JWT guarda roles como USER o ADMIN
        roles.setAuthoritiesClaimName("roles");

        // Spring los convierte a ROLE_USER / ROLE_ADMIN
        roles.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(roles);

        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(Customizer.withDefaults())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLICO
                        // =========================

                        // Registro y login
                        .requestMatchers(
                                "/auth/**"
                        ).permitAll()

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()


                        // =========================
                        // ADMIN
                        // =========================

                        // Todo lo que este bajo /api/admin
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")


                        // -------------------------
                        // PROPIETARIOS
                        // -------------------------

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/owners",
                                "/api/owners/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/owners",
                                "/api/owners/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/owners",
                                "/api/owners/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/owners",
                                "/api/owners/**"
                        ).hasRole("ADMIN")


                        // -------------------------
                        // CATALOGO DE VACUNAS
                        // -------------------------

                        // Crear vacunas solo ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/vaccines",
                                "/api/vaccines/**"
                        ).hasRole("ADMIN")

                        // Actualizar vacunas solo ADMIN
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/vaccines",
                                "/api/vaccines/**"
                        ).hasRole("ADMIN")

                        // Eliminar vacunas solo ADMIN
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/vaccines",
                                "/api/vaccines/**"
                        ).hasRole("ADMIN")


                        // =========================
                        // USER + ADMIN
                        // =========================

                        // -------------------------
                        // MASCOTAS
                        // -------------------------

                        // USER puede registrar sus propias mascotas.
                        // ADMIN tambien puede registrar.
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/pets",
                                "/api/pets/**"
                        ).hasAnyRole(
                                "USER",
                                "ADMIN"
                        )


                        // -------------------------
                        // VACUNACIONES
                        // -------------------------

                        // USER puede registrar vacunaciones
                        // unicamente sobre sus mascotas.
                        // Esa validacion se realiza en Application.
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/vaccinations",
                                "/api/vaccinations/**"
                        ).hasAnyRole(
                                "USER",
                                "ADMIN"
                        )


                        // -------------------------
                        // CONSULTAS
                        // -------------------------

                        // USER y ADMIN pueden consultar.
                        // Los servicios filtran los recursos
                        // segun el propietario autenticado.
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/**"
                        ).hasAnyRole(
                                "USER",
                                "ADMIN"
                        )


                        // -------------------------
                        // ACTUALIZACIONES
                        // -------------------------

                        // USER puede actualizar sus propios recursos.
                        // ADMIN puede actualizar cualquier recurso permitido.
                        // El ownership se valida en Application.
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/**"
                        ).hasAnyRole(
                                "USER",
                                "ADMIN"
                        )


                        // -------------------------
                        // ELIMINACIONES
                        // -------------------------

                        // USER puede eliminar sus propios recursos.
                        // ADMIN puede eliminar cualquier recurso permitido.
                        // El ownership se valida en Application.
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/**"
                        ).hasAnyRole(
                                "USER",
                                "ADMIN"
                        )


                        // Todo lo demas necesita autenticacion
                        .anyRequest()
                        .authenticated()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter
                                )
                        )
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config =
                new CorsConfiguration();

        // Frontend local
        config.setAllowedOrigins(
                List.of(
                        "http://localhost:4200"
                )
        );

        config.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        config.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"
                )
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                config
        );

        return source;
    }
}