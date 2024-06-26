package com.tacos.api.repo;

import com.tacos.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;


public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Ingredient findByName(String name);

}
