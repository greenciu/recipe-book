package org.example.recipes.service;

import org.example.recipes.dao.RecipeRepository;
import org.example.recipes.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(@Valid Recipe recipe) {
        validateRecipe(recipe);

        recipe.setId(null);
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
            throw new NotFoundException(String.format("Recipe %s not found!", recipe.getId()));
        }
    }

    public void deleteRecipe(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            recipeRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("Recipe %s not found!", id));
        }
    }

    public Recipe getById(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new NotFoundException(String.format("Recipe %s not found!", id));
        }
    }

    public List<Recipe> getAll() {
        return StreamSupport
                .stream(recipeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    private void validateRecipe(Recipe recipe) {
        Assert.isTrue(recipe.getType() != null, "recipe.type is required");
        Assert.isTrue(recipe.getServings() != 0, "recipe.servings is required");
        Assert.isTrue(recipe.getIngredients() != null, "recipe.ingredients is required");
        Assert.isTrue(recipe.getInstructions() != null, "recipe.instructions is required");
    }
}
