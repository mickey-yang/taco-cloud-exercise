package com.tacos.api.controllers;

import com.tacos.api.domain.Ingredient;
import com.tacos.api.domain.Taco;
import com.tacos.api.domain.TacoOrder;
import com.tacos.api.repository.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

@Slf4j
@SessionAttributes("tacoOrderModelAttribute")
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // All model attributes are invoked when a GET request is made
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        // Loop adds k:v pairs to Model. e.g. ("wrap", {Ingredient FLTO, Ingredient COTO})
    }

    @ModelAttribute(name = "tacoModelAttribute")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "tacoOrderModelAttribute")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco
            , Errors errors
            , @ModelAttribute TacoOrder tacoOrder) {
        // @ModelAttribute TacoOrder indicates the method should use the TacoOrder placed within the Model
        if (errors.hasErrors()) {
            return "design";
        }

        tacoOrder.addTaco(taco);
        LOG.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Ingredient.Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(ingredient -> ingredient.getType().equals(type))
                .toList();
    }

}
