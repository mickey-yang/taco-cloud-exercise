package com.tacos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient implements Serializable {

    @Id
    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
