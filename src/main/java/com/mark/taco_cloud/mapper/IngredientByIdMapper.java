package com.mark.taco_cloud.mapper;

import com.mark.taco_cloud.domain.dto.Ingredient;
import com.mark.taco_cloud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;

import com.mark.taco_cloud.domain.dto.Ingredient.Type;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdMapper implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientByIdMapper(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }
}
