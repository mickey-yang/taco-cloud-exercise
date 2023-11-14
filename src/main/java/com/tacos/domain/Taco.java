package com.tacos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Taco implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime createdAt;

    @NotNull
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @ManyToMany()
    private List<Ingredient> ingredients;

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    public static class TacoBuilder {

        private String name = "No Name";
        private List<Ingredient> ingredients = new ArrayList<>();

        public TacoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TacoBuilder withIngredientsList(List<Ingredient> list) {
            this.ingredients = ingredients;
            return this;
        }

        public Taco build() {
            Taco result = new Taco();
            result.setName(name);
            result.setIngredients(ingredients);
            return result;
        }
    }


}
