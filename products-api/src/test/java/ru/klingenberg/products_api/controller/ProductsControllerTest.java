package ru.klingenberg.products_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.klingenberg.products_api.db.entity.Product;
import ru.klingenberg.products_api.dto.MeasurementUnit;
import ru.klingenberg.products_api.dto.ProductDto;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class ProductsControllerTest {

//    @Autowired
    ProductsController productsController;

    ProductDto productDto = new ProductDto()
            .setName("Тестовый продукт")
            .setAmount(600d)
            .setMeasurementUnit(MeasurementUnit.g)
            .setPrice(129.99);


//    @Test
    void addAndFetchProduct() {
        Product product = addProduct(productDto);
        ProductDto productDto1 = getById(product.getId());
        Assert.isTrue(ProductDto.from(product).equals(productDto1), "addAndFetchProduct test failed");
    }

    Product addProduct(ProductDto productDto) {
        return productsController.addProduct(productDto);
    }

    ProductDto getById(String id) {
        return productsController.getById(id);
    }

}