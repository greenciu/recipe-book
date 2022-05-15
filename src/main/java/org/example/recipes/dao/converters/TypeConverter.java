package org.example.recipes.dao.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.example.recipes.model.Recipe;

public class TypeConverter implements DynamoDBTypeConverter<String, Recipe.Type> {

    @Override
    public String convert(Recipe.Type type) {
        return type.toString();
    }

    @Override
    public Recipe.Type unconvert(String s) {
        return Recipe.Type.valueOf(s);
    }
}
