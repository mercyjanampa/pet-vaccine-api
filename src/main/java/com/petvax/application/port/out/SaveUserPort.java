package com.petvax.application.port.out;

import com.petvax.domain.model.User;

public interface SaveUserPort {

    User save(User user);
}