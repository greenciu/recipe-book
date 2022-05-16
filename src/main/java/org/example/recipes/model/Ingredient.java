package org.example.recipes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.recipes.dao.converters.UnitConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@DynamoDBDocument
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    public enum Unit {
        gram
    }

    @NotBlank
    private String name;
    @NotNull
    private int amount;
    @DynamoDBTypeConverted(converter = UnitConverter.class)
    private Unit unit;
}
