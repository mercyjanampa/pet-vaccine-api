package com.petvax.infrastructure.security;

import com.petvax.application.port.out.LoadUserPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final LoadUserPort loadUserPort;

    public CustomUserDetailsService(
            LoadUserPort loadUserPort
    ) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        return loadUserPort.findByEmail(email)
                .map(SecurityUser::from)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado"
                        )
                );
    }
}