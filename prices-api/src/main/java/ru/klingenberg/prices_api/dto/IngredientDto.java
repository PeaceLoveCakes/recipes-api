package ru.klingenberg.prices_api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IngredientDto {

    @JsonAlias("ingredientId")
    private String id;

    private String name;

    private Double amount;

    private MeasurementUnit measurementUnit;

    private List<ProductDto> options;

}
