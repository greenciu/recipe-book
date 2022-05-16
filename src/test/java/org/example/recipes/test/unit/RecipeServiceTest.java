package org.example.recipes.test.unit;

import org.example.recipes.dao.RecipeRepository;
import org.example.recipes.model.Ingredient;
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

import java.text.ParseException;
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
    }

    @DisplayName("Test save(recipe) with incomplete type")
    @Test
    public void givenIncompleteTypeInRecipeObject_whenSaveRecipe_thenThrowInvalid() {
        Recipe testRecipe = Recipe.builder().build();

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recipeService.createRecipe(testRecipe);
        });

        Assertions.assertEquals("recipe.type is required", e.getMessage());
    }

    @DisplayName("Test save(recipe) with incomplete servings")
    @Test
    public void givenIncompleteServingsInRecipeObject_whenSaveRecipe_thenThrowInvalid() {
        Recipe testRecipe = Recipe.builder().type(Recipe.Type.VEGAN).build();

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recipeService.createRecipe(testRecipe);
        });

        Assertions.assertEquals("recipe.servings is required", e.getMessage());
    }

    @DisplayName("Test save(recipe) with incomplete ingredients")
    @Test
    public void givenIncompleteIngredientsInRecipeObject_whenSaveRecipe_thenThrowInvalid() {
        Recipe testRecipe = TestObjects.createRecipeObject();
        testRecipe.setIngredients(null);

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recipeService.createRecipe(testRecipe);
        });

        Assertions.assertEquals("recipe.ingredients is required", e.getMessage());
    }

    @DisplayName("Test save(recipe) with incomplete instructions")
    @Test
    public void givenIncompleteInstructionsInRecipeObject_whenSaveRecipe_thenThrowInvalid() {
        Recipe testRecipe = TestObjects.createRecipeObject();
        testRecipe.setInstructions(null);

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recipeService.createRecipe(testRecipe);
        });

        Assertions.assertEquals("recipe.instructions is required", e.getMessage());
    }

    @DisplayName("Test update(recipe) when entity is missing from data-store")
    @Test
    public void givenRecipeObject_whenUpdateNonExistingRecipe_thenThrowNotFound() {
        Recipe testRecipe = TestObjects.createRecipeObject();

        NotFoundException e = Assertions.assertThrows(NotFoundException.class, () -> {
            recipeService.updateRecipe(testRecipe);
        });

        Assertions.assertEquals("Recipe 1 not found!", e.getMessage());
    }

    @DisplayName("Test update(recipe)")
    @Test
    public void givenRecipeObject_whenUpdateRecipe_thenReturnUpdatedRecipeObject() {
        Ingredient newIngredient = Ingredient.builder()
                .name("olives")
                .amount(150)
                .unit(Ingredient.Unit.gram)
                .build();

        Recipe testRecipe = TestObjects.createRecipeObject();
        testRecipe.setType(Recipe.Type.CARNIVORE);
        testRecipe.getIngredients().add(newIngredient);

        given(recipeRepository.findById(testRecipe.getId())).willReturn(Optional.of(testRecipe));
        given(recipeRepository.save(testRecipe)).willReturn(testRecipe);

        recipeService.updateRecipe(testRecipe);

        Recipe recipe = recipeService.getById(testRecipe.getId());
        log.debug("Updated entity: {}", recipe);
        Assertions.assertNotNull(recipe);
        Assertions.assertEquals(Recipe.Type.CARNIVORE, recipe.getType());
        Assertions.assertTrue(recipe.getIngredients().contains(newIngredient));
    }

    @DisplayName("Test getById(recipeId)")
    @Test
    public void givenRecipeObject_whenGetRecipe_thenReturnRecipeObject() throws ParseException {
        Recipe testRecipe = TestObjects.createRecipeObject();
        given(recipeRepository.findById("1")).willReturn(Optional.of(testRecipe));

        Recipe recipe = recipeService.getById("1");
        log.debug("Got entity: {}", recipe);
        Assertions.assertNotNull(recipe);
    }

    @DisplayName("Test getById(recipeId) when entity is missing from data-store")
    @Test
    public void givenRecipeObject_whenGetRecipeWithNonExistentId_thenThrowNotFound() {
        Recipe testRecipe = TestObjects.createRecipeObject();

        NotFoundException e = Assertions.assertThrows(NotFoundException.class, () -> {
            recipeService.getById(testRecipe.getId());
        });

        Assertions.assertEquals("Recipe 1 not found!", e.getMessage());
    }

    @DisplayName("Test delete(recipeId)")
    @Test
    public void givenRecipeObject_whenDeleteRecipeWithNonExistentId_thenThrowNotFound() {
        Recipe testRecipe = TestObjects.createRecipeObject();

        NotFoundException e = Assertions.assertThrows(NotFoundException.class, () -> {
            recipeService.deleteRecipe(testRecipe.getId());
        });

        Assertions.assertEquals("Recipe 1 not found!", e.getMessage());
    }
}
