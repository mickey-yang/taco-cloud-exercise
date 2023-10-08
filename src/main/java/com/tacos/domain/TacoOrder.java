package com.tacos.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDate placedAt;

    @NotBlank(message="Delivery name is required")
    private String deliveryName;
    @NotBlank(message="Street name is required")
    private String deliveryStreet;
    @NotBlank(message="City name is required")
    private String deliveryCity;
    @NotBlank(message="Postal code is required")
    private String deliveryPostalCode;
    @CreditCardNumber(message="Invalid credit card number")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2]([\\/])([2-9][0-9])$)",
            message="Expiry date must be in format MM/YY")
    private String ccExpiration;
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }

}
