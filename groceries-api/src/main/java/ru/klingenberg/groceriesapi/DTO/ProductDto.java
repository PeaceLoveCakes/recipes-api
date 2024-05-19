package ru.klingenberg.groceriesapi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

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

}
