package com.petvax.infrastructure.security;

import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final Role role;
    private final Long ownerId;

    public SecurityUser(
            Long id,
            String email,
            String password,
            Role role,
            Long ownerId
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ownerId = ownerId;
    }

    public static SecurityUser from(User user) {
        return new SecurityUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getOwnerId()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                )
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}