package com.mark.taco_cloud;

import com.mark.taco_cloud.domain.dto.Ingredient;
import com.mark.taco_cloud.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataR2dbcTest
public class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepository;

    @BeforeEach
    public void setup() {
        Flux<Ingredient> deleteAndInsert = ingredientRepository.deleteAll()
                .thenMany(ingredientRepository.saveAll(
                        Flux.just(
                                new Ingredient("FLTO", "Flout Tortilla", Ingredient.Type.WRAP),
                                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                                new Ingredient("CHED", "Cheddar Cheese", Ingredient.Type.CHEESE)
                        )
                ));
        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetchIngredients() {
        StepVerifier.create(ingredientRepository.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                    assertThat(ingredients).hasSize(3);
                    assertThat(ingredients).contains(
                            new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
                    assertThat(ingredients).contains(
                            new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
                    assertThat(ingredients).contains(
                            new Ingredient("CHED", "Cheddar Cheese", Ingredient.Type.CHEESE));
                })
                .verifyComplete();
        StepVerifier.create(ingredientRepository.findBySlug("FLTO"))
                .assertNext(ingredient -> {
                    ingredient.equals(new Ingredient("FLTO", "Flour Tortilla",
                            Ingredient.Type.WRAP));
                });
    }

}
