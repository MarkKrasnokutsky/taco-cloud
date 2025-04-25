package com.mark.taco_cloud.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    private Long id;

    @NotNull
    @Size(min=5, message="Имя должно содержать минимум 5 символов")
    private String name;

    @NotNull
    @Size(min = 1, message = "Ингридиентов должно быть минимум 1")
    private List<IngredientRef> ingredientsRef;

    private Date createdAt = new Date();
}
