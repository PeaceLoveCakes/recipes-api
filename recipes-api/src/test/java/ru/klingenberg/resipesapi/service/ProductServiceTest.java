package ru.klingenberg.resipesapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.klingenberg.resipesapi.dto.ProductDto;
import ru.klingenberg.resipesapi.db.entity.Product;
import ru.klingenberg.resipesapi.model.MeasurementUnit;

@SpringBootTest
class ProductServiceTest {

//    @Autowired
//    ProductService productService;
//
//    @Test
//    void saveAndGet() {
//        Double price = 249.99;
//        Double amount = 1d;
//        ProductDto productDto = new ProductDto()
//                .setName("Помидор")
//                .setAmount(amount)
//                .setMeasurementUnit(MeasurementUnit.kg)
//                .setPrice(price);
//        Product result = productService.save(productDto);
//    }
}