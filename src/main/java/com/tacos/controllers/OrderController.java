package com.tacos.controllers;

import com.tacos.domain.TacoOrder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrderModelAttribute")
public class OrderController {

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(
            @Valid
            @ModelAttribute("tacoOrderModelAttribute") TacoOrder order
            ,Errors errors
            ,SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            LOG.error("Form has errors");
            errors.getAllErrors().forEach(System.out::println);
            return "orderForm";
        }

        LOG.info("Order submitted: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

}
