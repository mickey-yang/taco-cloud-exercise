package com.tacos.domain;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private final String username;
    private final String password;
    private final String fullName;
    private final String street;
    private final String city;
    private final String postalCode;
    private final String phone;

    public RegisteredUser toUser(PasswordEncoder passwordEncoder) {
        return new RegisteredUser(
                username, passwordEncoder.encode(password),
                fullName, street, city, postalCode, phone
        );
    }

}
