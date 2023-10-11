package com.tacos.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Data
@Table("Taco_Order")
public class Taco {

    @Id
    private Long id;
    private LocalDate createdAt;

    @NotNull
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;
    @NotNull
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;


}
