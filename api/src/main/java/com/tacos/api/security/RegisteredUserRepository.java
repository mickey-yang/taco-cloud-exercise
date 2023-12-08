package com.tacos.api.security;

import com.tacos.api.security.RegisteredUser;
import org.springframework.data.repository.CrudRepository;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long> {

    RegisteredUser findByUsername(String username);

}
