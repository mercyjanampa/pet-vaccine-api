package com.petvax.infrastructure.security;

import com.petvax.application.port.out.TokenIssuerPort;
import com.petvax.domain.model.Role;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class JwtTokenIssuer
        implements TokenIssuerPort {

    private final JwtEncoder jwtEncoder;

    public JwtTokenIssuer(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public String issue(
            String email,
            Role role
    ) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("petvax-api")
                .subject(email)
                .issuedAt(now)
                .expiresAt(
                        now.plus(
                                15,
                                ChronoUnit.MINUTES
                        )
                )
                .claim(
                        "roles",
                        List.of(role.name())
                )
                .build();

        JwsHeader header =
                JwsHeader.with(
                        MacAlgorithm.HS256
                ).build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(
                        header,
                        claims
                )
        ).getTokenValue();
    }
}