package org.example.recipes.test.util;

import org.example.recipes.model.Ingredient;
import org.example.recipes.model.Recipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestObjects {

    public static List<Ingredient> createIngredientsList() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(createIngredient("salt", 10));
        ingredients.add(createIngredient("pepper", 5));
        return ingredients;
    }

    public static Recipe createRecipeObject() {
        return Recipe.builder()
                .id("1")
                .created(LocalDateTime.now())
                .type(Recipe.Type.VEGETARIAN)
                .servings(new Random().nextInt(4) + 1)
                .ingredients(createIngredientsList())
                .instructions("prepare")
                .build();
    }

    private static Ingredient createIngredient(String name, int amount) {
        return Ingredient.builder()
                .name(name)
                .amount(amount)
                .unit(Ingredient.Unit.gram)
                .build();
    }
}
