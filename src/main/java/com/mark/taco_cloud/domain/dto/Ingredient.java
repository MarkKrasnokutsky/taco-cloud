package com.mark.taco_cloud.domain.dto;

import jakarta.persistence.Id;
import lombok.*;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Ingredient {
    @Id
    private Long id;
    private @NonNull String slug;
    private @NonNull String name;
    private @NonNull Type type;
    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}