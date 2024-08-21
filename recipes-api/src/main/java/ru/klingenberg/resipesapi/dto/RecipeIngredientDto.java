package ru.klingenberg.resipesapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDto {

    private String ingredientId;
    private String name;
    private Double amount;
    private MeasurementUnit measurementUnit;

    public RecipeIngredientDto setMeasurementUnit(MeasurementUnit measurementUnit){
        this.measurementUnit = measurementUnit;
        return this;
    }

    public RecipeIngredientDto setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = switch (measurementUnit.toLowerCase().trim()) {
            case "kg", "g", "l", "ml", "unit" -> MeasurementUnit.valueOf(measurementUnit);
            case "кг", "килограмм" -> MeasurementUnit.kg;
            case "г", "грамм" -> MeasurementUnit.g;
            case "л", "литр" -> MeasurementUnit.l;
            case "мл", "миллилитр" -> MeasurementUnit.ml;
            default -> MeasurementUnit.unit;
        };
        return this;
    }

}
