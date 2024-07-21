package ru.klingenberg.products_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.klingenberg.products_api.db.entity.Product;

@Getter
@Setter
@ToString
public class ProductDto {

    private String id;

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

    public static ProductDto from(Product product){
        return new ProductDto()
                .setId(product.getId())
                .setName(product.getName())
//                .setPrice(product.getPrice())
//                .setAmount(product.getAmount())
                .setMeasurementUnit(product.getMeasurementUnit());
    }

    public void normalizeAmount() {
        Double amount = this.getAmount();
        if (amount != 1d) {
            Double multiply = 1 / amount;
            this.setAmount(amount * multiply);
            this.setPrice(Math.floor(this.getPrice() * multiply * 100) / 100);
        }
    }
}
