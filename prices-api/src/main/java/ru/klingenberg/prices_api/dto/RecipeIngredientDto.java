package ru.klingenberg.prices_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDto {

    private String ingredientId;
    private String name;
    private Double amount;
    private MeasurementUnit measurementUnit;

}
