package com.tacos.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDate placedAt;

    @Column("delivery_name")
    @NotBlank(message="Delivery name is required")
    private String deliveryName;
    @Column("delivery_street")
    @NotBlank(message="Street name is required")
    private String deliveryStreet;
    @Column("delivery_city")
    @NotBlank(message="City name is required")
    private String deliveryCity;
    @Column("delivery_postal_code")
    @NotBlank(message="Postal code is required")
    private String deliveryPostalCode;
    @Column("cc_number")
    @CreditCardNumber(message="Invalid credit card number")
    private String ccNumber;
    @Column("cc_expiration")
    @Pattern(regexp="^(0[1-9]|1[0-2]([\\/])([2-9][0-9])$)",
            message="Expiry date must be in format MM/YY")
    private String ccExpiration;
    @Column("cc_cvv")
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }

}
