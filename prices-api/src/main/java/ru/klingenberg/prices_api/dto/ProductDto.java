package ru.klingenberg.prices_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private String id;

    private String shopName;

    private String name;

    private Double price;

    private Double amount;

    private MeasurementUnit measurementUnit;

    public ProductDto normalizeAmount() {
        Double multiply = 1 / amount;
        return multiplyAmount(multiply);
    }

    public ProductDto multiplyAmount(Double multiply) {
        setAmount(amount * multiply);
        setPrice(Math.floor(this.getPrice() * multiply * 100) / 100);
        return this;
    }

    public ProductDto calculateForAmount(Double amount){
        normalizeAmount();
        multiplyAmount(amount);
        return this;
    }
}
