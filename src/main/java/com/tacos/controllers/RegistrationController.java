//package com.tacos.controllers;
//
//import com.tacos.domain.RegistrationForm;
//import com.tacos.security.RegisteredUserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/register")
//public class RegistrationController {
//
//    private RegisteredUserRepository registeredUserRepository;
//    private PasswordEncoder passwordEncoder;
//
//    public RegistrationController(RegisteredUserRepository registeredUserRepository, PasswordEncoder passwordEncoder) {
//        this.registeredUserRepository = registeredUserRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping
//    public String registerForm() {
//        return "registration";
//    }
//
//    @PostMapping
//    public String processRegistration(RegistrationForm form) {
//        registeredUserRepository.save(form.toUser(passwordEncoder));
//        return "redirect:/login";
//    }
//
//}
