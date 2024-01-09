package com.tacos.authentication.repo;

import com.tacos.authentication.domain.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    UserDetails findByUsername(String username);
}
