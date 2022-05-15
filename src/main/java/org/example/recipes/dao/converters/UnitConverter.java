package org.example.recipes.dao.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.example.recipes.model.Ingredient;

public class UnitConverter implements DynamoDBTypeConverter<String, Ingredient.Unit> {

    @Override
    public String convert(Ingredient.Unit unit) {
        return unit.toString();
    }

    @Override
    public Ingredient.Unit unconvert(String s) {
        return Ingredient.Unit.valueOf(s);
    }
}
