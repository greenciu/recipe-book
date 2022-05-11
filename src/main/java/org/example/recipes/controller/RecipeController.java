package org.example.recipes.controller;

import org.example.recipes.dao.RecipeRepository;
import org.example.recipes.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping(path = "/api/recipe")
    public List<Recipe> listRecipes() {
        return recipeRepository.findAll();
    }

    @PostMapping(path = "/api/recipe")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        recipe.setCreated(LocalDateTime.now());
        Recipe created = recipeRepository.save(recipe);
        return created;
    }
}
