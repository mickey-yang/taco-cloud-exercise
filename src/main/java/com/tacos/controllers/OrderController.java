package com.tacos.controllers;

import com.tacos.config.OrderConfig;
import com.tacos.domain.TacoOrder;
import com.tacos.repository.OrderRepository;
import com.tacos.security.RegisteredUser;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrderModelAttribute")
public class OrderController {

    private OrderRepository orderRepo;

    private OrderConfig orderConfig;

    @Autowired
    public OrderController(OrderRepository orderRepo, OrderConfig orderConfig) {
        this.orderRepo = orderRepo;
        this.orderConfig = orderConfig;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal RegisteredUser user
            , Model model) {

        Pageable pageable = PageRequest.of(0, orderConfig.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }

    @PostMapping
    public String processOrder(
            @Valid @ModelAttribute("tacoOrderModelAttribute") TacoOrder order
            , Errors errors
            , SessionStatus sessionStatus
            , @AuthenticationPrincipal RegisteredUser user) {
        if (errors.hasErrors()) {
            LOG.error("Form has errors");
            errors.getAllErrors().forEach(System.out::println);
            return "orderForm";
        }

        order.setUser(user);
        orderRepo.save(order);

        LOG.info("Order submitted: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
