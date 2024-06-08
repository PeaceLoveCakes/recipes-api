package ru.klingenberg.resipesapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.klingenberg.resipesapi.DTO.ProductDto;
import ru.klingenberg.resipesapi.db.entity.Product;
import ru.klingenberg.resipesapi.model.MeasurementUnit;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void saveLessThenOneKg() {
        Double price = 249.99;
        Double amount = 1d;
        Double multiply = 0.4;
        ProductDto productDto = new ProductDto()
                .setName("Помидор")
                .setAmount(amount * multiply)
                .setMeasurementUnit(MeasurementUnit.kg)
                .setPrice(price * multiply);
        Product result = productService.save(productDto);
        Assert.isTrue(price.equals(result.getPrice()), "save test failed \n" +
                "expected: " + price + "\n" +
                "got: " + result.getPrice());
    }

    @Test
    void saveLessMoreThenOneUnit() {
        Double price = 49d;
        Double amount = 1d;
        Double multiply = 7d;
        ProductDto productDto = new ProductDto()
                .setName("Помидор")
                .setAmount(amount * multiply)
                .setMeasurementUnit(MeasurementUnit.unit)
                .setPrice(price * multiply);
        Product result = productService.save(productDto);
        Assert.isTrue(price.equals(result.getPrice()), "save test failed \n" +
                "expected: " + price + "\n" +
                "got: " + result.getPrice());
    }
}