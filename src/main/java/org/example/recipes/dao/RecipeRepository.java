package org.example.recipes.dao;

import org.example.recipes.model.Recipe;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
