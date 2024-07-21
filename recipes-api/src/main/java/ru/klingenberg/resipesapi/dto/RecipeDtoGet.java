package ru.klingenberg.resipesapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeDtoGet {

    private String id;

    private String name;

    private String description;

    private List<ProductDto> products;

    private List<StepDto> steps;

}
