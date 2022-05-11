package org.example.recipes.dao.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class IngredientsConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return list.stream().collect(Collectors.joining(","));
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return Arrays.asList(s.split(","));
    }
}
