package com.tacos.api.repository;

import com.tacos.api.domain.RegisteredUser;
import org.springframework.data.repository.CrudRepository;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long> {

    RegisteredUser findByUsername(String username);

}
