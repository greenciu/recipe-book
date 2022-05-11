package org.example.recipes.dao;

import org.example.recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("recipeRepository")
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
