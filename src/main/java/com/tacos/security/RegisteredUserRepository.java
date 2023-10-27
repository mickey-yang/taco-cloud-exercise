package com.tacos.security;

import org.springframework.data.repository.CrudRepository;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long> {

    RegisteredUser findByUsername(String username);

}
