package org.example.recipes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.recipes.dao.converters.IngredientsConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    public enum Type {
        VEGAN,
        VEGETARIAN,
        CARNIVORE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @JsonFormat(pattern = "dd‐MM‐yyyy HH:mm")
    private LocalDateTime created;
    @JsonFormat(pattern = "dd‐MM‐yyyy HH:mm")
    private LocalDateTime updated;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int servings;
    @Convert(converter = IngredientsConverter.class)
    private List<String> ingredients;
    private String instructions;
}
