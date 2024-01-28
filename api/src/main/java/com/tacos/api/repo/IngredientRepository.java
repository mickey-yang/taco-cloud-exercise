package com.tacos.api.repo;

import com.tacos.api.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;


public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Ingredient findByName(String name);

}
