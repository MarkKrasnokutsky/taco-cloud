package com.mark.taco_cloud.repository;

import com.mark.taco_cloud.domain.dto.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {

    Mono<Ingredient> findBySlug(String slug);

}
