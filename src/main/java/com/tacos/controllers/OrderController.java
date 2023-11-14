package com.tacos.controllers;

import com.tacos.config.OrderConfig;
import com.tacos.domain.TacoOrder;
import com.tacos.repository.OrderRepository;
import com.tacos.security.RegisteredUser;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

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

    @PutMapping(path = "/{id}", consumes = "application/json")
    public TacoOrder putOrder(@PathVariable("id") Long id
            , @RequestBody TacoOrder order) {
        Optional<TacoOrder> result = orderRepo.findById(id);
        if (result.isPresent()) {
            order.setId(id);
        }
        return orderRepo.save(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("id") Long id) {
        try {
            orderRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }

}
