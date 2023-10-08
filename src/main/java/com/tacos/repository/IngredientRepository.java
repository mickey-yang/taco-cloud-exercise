package com.tacos.repository;

import com.tacos.domain.Ingredient;

import java.util.Optional;


public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

}
