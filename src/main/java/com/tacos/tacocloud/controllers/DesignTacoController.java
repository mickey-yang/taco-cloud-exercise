package com.tacos.tacocloud.controllers;

import com.tacos.tacocloud.domain.Taco;
import com.tacos.tacocloud.domain.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import com.tacos.tacocloud.domain.Ingredient;
import com.tacos.tacocloud.domain.Ingredient.Type;

@Slf4j
@SessionAttributes("tacoOrderModelAttribute")
@Controller
@RequestMapping("/design")
public class DesignTacoController {


    // All model attributes are invoked when a GET request is made
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
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
    public String processTaco(Taco taco, @ModelAttribute TacoOrder tacoOrder) {
        // @ModelAttribute TacoOrder indicates the method should use the TacoOrder placed within the Model
        tacoOrder.addTaco(taco);
        LOG.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .toList();
    }

}
