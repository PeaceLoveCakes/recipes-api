package ru.klingenberg.resipesapi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ru.klingenberg.resipesapi.model.MeasurementUnit;

@Getter
@Setter
public class ProductDto {

    @NotBlank(message = "name required")
    private String name;

    @NotNull(message = "price required")
    @Positive(message = "price must be positive")
    private Double price;

    @NotNull(message = "amount required")
    @Positive(message = "amount must be positive")
    private Double amount;

    @NotNull(message = "measurementUnit required")
    private MeasurementUnit measurementUnit;

    public ProductDto setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
        return this;
    }

    public ProductDto setMeasurementUnit(String measurementUnit) {
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
