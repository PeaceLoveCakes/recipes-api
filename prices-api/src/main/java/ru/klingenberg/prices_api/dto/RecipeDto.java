package ru.klingenberg.prices_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeDto {

    private String id;

    private String name;

    private String description;

    private List<IngredientDto> ingredients;

    private List<StepDto> steps;

}
