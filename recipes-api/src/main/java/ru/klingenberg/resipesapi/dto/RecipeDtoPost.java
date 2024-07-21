package ru.klingenberg.resipesapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipeDtoPost {

    @NotBlank(message = "name required")
    private String name;

    private String description;

    private List<RecipeProductDto> productIds = new ArrayList<>();

    private List<StepDto> steps = new ArrayList<>();

}
