package com.tacos.services;

import com.tacos.domain.Ingredient;
import org.springframework.stereotype.Service;

@Service
public interface IngredientService {

    Iterable<Ingredient> findAll();

    Ingredient addIngredient(Ingredient ingredient);
}
