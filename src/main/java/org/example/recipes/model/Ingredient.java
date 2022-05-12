package org.example.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    public enum Unit {
        Gram
    }

    private String name;
    private int amount;
    private Unit unit;
}
