package org.example.recipes.test.integration;

import org.apache.commons.codec.binary.Base64;
import org.example.recipes.model.Ingredient;
import org.example.recipes.model.Recipe;
import org.example.recipes.test.util.TestObjects;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {

    private static final Logger log = LoggerFactory.getLogger(RecipeControllerTest.class);
    private static RestTemplate restTemplate;

    private static Recipe testRecipe;

    @Value("${test.api.recipeUrl}")
    private String testEndpoint;
    @Value("${test.api.username}")
    private String testUsername;
    @Value("${test.api.password}")
    private String testPassword;

    @BeforeEach
    public void setup() {
        restTemplate = createTestClient();
    }

    @Test
    @Order(1)
    public void testCreateRecipeApi() {
        testRecipe = TestObjects.createRecipeObject();
        ResponseEntity<Recipe> response = restTemplate.exchange(testEndpoint, HttpMethod.POST, new HttpEntity<>(testRecipe, getHeaders()), Recipe.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getId());
        log.info("Created recipe with id: {}", response.getBody().getId());

        // keep for following tests
        testRecipe = response.getBody();
    }

    @Test
    @Order(2)
    public void testUpdateRecipeApi() {
        Ingredient newIngredient = Ingredient.builder()
                .name("olives")
                .amount(150)
                .unit(Ingredient.Unit.gram)
                .build();

        testRecipe.getIngredients().add(newIngredient);
        HttpEntity<Recipe> recipeUpdateRequest = new HttpEntity<>(testRecipe, getHeaders());
        ResponseEntity<Recipe> response = restTemplate.exchange(testEndpoint,
                HttpMethod.PUT, recipeUpdateRequest, Recipe.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getIngredients().contains(newIngredient));
        log.info("Updated recipe with id: {}", response.getBody().getId());

        testRecipe = response.getBody();
    }

    @Test
    @Order(3)
    public void testDeleteRecipeApi() {
        String url = String.format("%s/%s", testEndpoint, testRecipe.getId());
        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(getHeaders()), Void.class);

        log.info("Removed recipe with id: {}", testRecipe.getId());
    }

    private RestTemplate createTestClient() {
        // HTTP proxy to capture traffic for debugging purposes
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8866));
        requestFactory.setProxy(proxy);

        return new RestTemplate(requestFactory);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Basic %s", getBase64Credentials()));
        return headers;
    }

    private String getBase64Credentials() {
        String credentials = String.format("%s:%s", testUsername, testPassword);
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes(StandardCharsets.US_ASCII));
        return new String(encodedCredentials);
    }
}
