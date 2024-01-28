package com.tacos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TacoOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate placedAt;

    @ManyToOne
    private RegisteredUser user;

    @Column(name = "delivery_name")
    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @Column(name = "delivery_street")
    @NotBlank(message="Street name is required")
    private String deliveryStreet;

    @Column(name = "delivery_city")
    @NotBlank(message="City name is required")
    private String deliveryCity;

    @Column(name = "delivery_postal_code")
    @NotBlank(message="Postal code is required")
    private String deliveryPostalCode;

    @Column(name = "cc_number")
    @CreditCardNumber(message="Invalid credit card number")
    private String ccNumber;

    @Column(name = "cc_expiration")
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Expiry date must be in format MM/YY")
    private String ccExpiration;

    @Column(name = "cc_cvv")
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }

}
