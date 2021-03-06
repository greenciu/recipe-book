package org.example.recipes.controller;

import org.example.recipes.model.Recipe;
import org.example.recipes.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RecipeController {

    private static final Logger log = LoggerFactory.getLogger(RecipeController.class);

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(path = "/api/recipe")
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe createdRecipe = recipeService.createRecipe(recipe);
        log.info("Created recipe with id: {}", createdRecipe.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRecipe);
    }

    @PutMapping(path = "/api/recipe")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.updateRecipe(recipe);
        log.info("Updated recipe with id: {}", recipe.getId());
        return ResponseEntity
                .ok(updatedRecipe);
    }

    @DeleteMapping(path = "/api/recipe/{id}")
    public void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
        log.info("Removed recipe with id: {}", id);
    }

    @GetMapping(path = "/api/recipe")
    public List<Recipe> getRecipes(@RequestParam(required = false) Recipe.Type type) {
        return recipeService.getAll();
    }

    @GetMapping(path = "/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable String id) {
        return recipeService.getById(id);
    }
}
