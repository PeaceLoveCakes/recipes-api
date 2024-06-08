package ru.klingenberg.magnit_api.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProductDto {

    private String name;

    private Double price;

    private Double amount;

    private String measurementUnit;

}
