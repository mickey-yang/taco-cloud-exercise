package com.tacos.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    In-memory user details service
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> usersList = new ArrayList<>();
        usersList.add(new User(
                        "Woody", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
        usersList.add(new User(
                        "Buzz", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
                )
        );
        return new InMemoryUserDetailsManager(usersList);
    }

//     Persisted user details service
//    @Bean
//    public UserDetailsService userDetailsService(RegisteredUserRepository userRepository) {
//        return username -> {
//            RegisteredUser user = userRepository.findByUsername(username);
//            if (user != null) return user;
//
//            throw new UsernameNotFoundException("User '" + username + "' not found");
//        };
//    }

    @Bean
    public MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(mvc.pattern("/design"), mvc.pattern("/orders")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/admin/**")).hasRole("ADMIN")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/ingredients")).hasAuthority("SCOPE_writeIngredients")
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api//ingredients")).hasAuthority("SCOPE_deleteIngredients")
                        .requestMatchers(mvc.pattern("/"), mvc.pattern("/**")).permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/design"))
                .oauth2Login(config -> config
                        .loginPage("/login"))
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer(oath2 -> oath2.jwt(Customizer.withDefaults()))
                .logout(Customizer.withDefaults())
                .build();

    }


}
