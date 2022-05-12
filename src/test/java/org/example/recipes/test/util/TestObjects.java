package org.example.recipes.test.util;

import org.example.recipes.model.Recipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestObjects {

    public static List<String> createIngredientsList() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("salt");
        ingredients.add("pepper");
        return ingredients;
    }

    public static Recipe createRecipeObject() {
        return Recipe.builder()
                .id(1)
                .created(LocalDateTime.now())
                .type(Recipe.Type.VEGETARIAN)
                .servings(new Random().nextInt(4) + 1)
                .ingredients(createIngredientsList())
                .instructions("prepare")
                .build();
    }
}
