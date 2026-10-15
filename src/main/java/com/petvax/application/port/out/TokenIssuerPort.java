package com.petvax.application.port.out;

import com.petvax.domain.model.Role;

public interface TokenIssuerPort {

    String issue(String email, Role role);
}