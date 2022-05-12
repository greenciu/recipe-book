package org.example.recipes.service;

import org.example.recipes.dao.RecipeRepository;
import org.example.recipes.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(Recipe recipe) {
        recipe.setCreated(LocalDateTime.now());
        recipe.setUpdated(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Recipe recipe) {
        Optional<Recipe> existingRecipe = recipeRepository.findById(recipe.getId());
        if (existingRecipe.isPresent()) {
            // maintain created date-time
            recipe.setCreated(existingRecipe.get().getCreated());
            recipe.setUpdated(LocalDateTime.now());
            Recipe updated = recipeRepository.save(recipe);
            return updated;
        } else {
            throw new NotFoundException(String.format("Recipe %d not found!", recipe.getId()));
        }
    }

    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }

    public Recipe findById(long id) {
        return recipeRepository.findById(id).get();
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }
}
