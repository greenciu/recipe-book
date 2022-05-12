package org.example.recipes.test.unit;

import org.example.recipes.dao.RecipeRepository;
import org.example.recipes.model.Recipe;
import org.example.recipes.service.RecipeService;
import org.example.recipes.test.util.TestObjects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webjars.NotFoundException;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RecipeServiceTest.class);

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    private void setup() {
    }

    @DisplayName("Test save(recipe)")
    @Test
    public void givenRecipeObject_whenSaveRecipe_thenReturnCreatedRecipeObject() {
        Recipe testRecipe = TestObjects.createRecipeObject();
        given(recipeRepository.save(testRecipe)).willReturn(testRecipe);

        Recipe recipe = recipeService.createRecipe(testRecipe);
        log.debug("Saved entity: {}", recipe);
        Assertions.assertNotNull(recipe);
        Assertions.assertEquals(1L, recipe.getId());
    }

    @Test
    public void givenRecipeObject_whenUpdateNonExistingRecipe_thenThrowNotFound() {
        Recipe testRecipe = TestObjects.createRecipeObject();

        NotFoundException e = Assertions.assertThrows(NotFoundException.class, () -> {
            recipeService.updateRecipe(testRecipe);
        });

        Assertions.assertEquals("Recipe 1 not found!", e.getMessage());
    }

    @Test
    public void givenRecipeObject_whenUpdateRecipe_thenReturnUpdatedRecipeObject() {
        Recipe testRecipe = TestObjects.createRecipeObject();
        testRecipe.setType(Recipe.Type.CARNIVORE);
        testRecipe.getIngredients().add("olives");

        given(recipeRepository.findById(testRecipe.getId())).willReturn(Optional.of(testRecipe));
        given(recipeRepository.save(testRecipe)).willReturn(testRecipe);

        Recipe recipe = recipeService.updateRecipe(testRecipe);
        log.debug("Updated entity: {}", recipe);
        Assertions.assertNotNull(recipe);
        Assertions.assertEquals(1L, recipe.getId());
    }
}
