package com.tacos.authentication;

import com.tacos.authentication.domain.AuthUser;
import com.tacos.authentication.repo.AuthUserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TacoAuthenticationMain {

    public static void main(String[] args) {
        SpringApplication.run(TacoAuthenticationMain.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            authUserRepository.save(new AuthUser("habuma", passwordEncoder.encode("password"), "ROLE_ADMIN"));
            authUserRepository.save(new AuthUser("tacochef", passwordEncoder.encode("password"), "ROLE_ADMIN"));
        };
    }
}
