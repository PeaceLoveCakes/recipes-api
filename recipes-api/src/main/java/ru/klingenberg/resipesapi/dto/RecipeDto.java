package ru.klingenberg.resipesapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeDto {

    private String id;

    @NotBlank(message = "name required")
    private String name;

    private String description;

    private List<RecipeIngredientDto> ingredients;

    private List<StepDto> steps;

}
