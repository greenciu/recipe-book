package org.example.recipes.model;

import lombok.Data;
import org.example.recipes.dao.converters.IngredientsConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Recipe {

    public enum Type {
        VEGAN,
        VEGETARIAN,
        CARNIVORE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @DateTimeFormat(pattern = "dd‐MM‐yyyy HH:mm")
    private LocalDateTime created;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int servings;
    @Convert(converter = IngredientsConverter.class)
    private List<String> ingredients;
    private String instructions;
}
