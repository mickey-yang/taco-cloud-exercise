package com.tacos.api.repo;

import com.tacos.domain.RegisteredUser;
import org.springframework.data.repository.CrudRepository;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long> {

    RegisteredUser findByUsername(String username);

}
