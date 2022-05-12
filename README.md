# Recipe Book Web-App

Example project implementing an API that allows to create/view/manage your recipes!

### Requirements

1. REST application to manage (CRUD) recipe objects
2. Persistence of recipe information
3. Production ready (testing, API documentation, standard packaging/Docker image, deployment descriptors, security-layer)

### High-level design

![Recipe service high-level diagram](./img/recipe_hld.drawio.png "Recipe service high-level diagram")

Decisions & assumptions:

1. Stateless service implementation, containerized and horizontally scalable across AZs & regions
2. Persistent data-store capable of handling high-volume write operations
3. Data model
   ````
   {
     "id": "1000001",
     "created": "12-05-2022 10:47",
     "type": "VEGETARIAN",          // | VEGAN | CARNIVORE
     "servings": 4,
     "duration": "90",
     "ingredients": [
       {"name": "flour", "amount": 200, "unit": "gram"},
       {"name": "salt", "amount": 7, "unit": "gram"},
       {"name": "dry yeast", "amount": 10, "unit": "gram"},
       {"name": "tomatto sauce", "amount": 250, "unit": "gram"},
       {"name": "mozzarella", "amount": 200, "unit": "gram"},
       {"name": "olive oil", "amount": 20, "unit": "gram"},
     ],
     "instructions": "prepare dough, preheat oven, bake, serve"
   }
   ````
   Although _Ingredient_ is a distinct object with defined attributes, the list of ingredients for a recipe is not persisted in another table.  
   The reasons being:
   1. They belong together from perspective of querying (updating, removing etc) a recipe.
   2. Queries where individual and/or all ingredients are important to know (for all or related recipes) are mainly for analytics (ie how many ingredients are used in all recipes, how many recipes use ingredient X etc).  And even for functionality depending on aggregating ingredients, it's possible to query the recipe-table and expand the ingredients column before aggregation occurs.
   3. Data normalized this way would not benefit from storage savings, the amounts & units are close to unique per recipe. 

4. Container image for the application is created during application build (separate profile that can be enabled). Since the service is stateless it can be easily scaled as long as the data-store is scaled with it to support all connections from all service instances.
5. Security layer can be implemented at the service side ie using Spring-Security.
   1. However it is a good practice to push some of these concerns out of the service implementation and into an upstream layer such as API Gateway or a side-car proxy, components that are more capable of handling authentication, authorization and throttling/quota for incoming requests, thus limiting the amount of traffic that gets to be handled by the service.

### Prerequisites

- Java 11+
- Maven 3.8+
- Docker engine

### Build

````
mvn clean install
````

### Run

````
mvn spring-boot:run
````

Access: [/api-docs](http://localhost:8080/api-docs) or [/swagger-ui](http://localhost:8080/swagger-ui/index.html) endpoints for documentation.

### Test

````
mvn clean test
````

## References
