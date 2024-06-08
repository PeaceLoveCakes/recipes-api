package ru.klingenberg.goods_collector.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private String name;

    private String shop;

    private String inShopId;

    private Double price;

    private Double amount;

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
