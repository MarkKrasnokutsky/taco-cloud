package com.mark.taco_cloud.domain.dto;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class IngredientRef {

    private final String ingredient;

}
