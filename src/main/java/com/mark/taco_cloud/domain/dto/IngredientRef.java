package com.mark.taco_cloud.domain.dto;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "ingredient_ref")
public class IngredientRef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ingredient ingredient;

    @ManyToOne
    private Taco taco;

    private Long tacoKey;
}
