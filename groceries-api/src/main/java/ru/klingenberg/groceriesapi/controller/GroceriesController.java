package ru.klingenberg.groceriesapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.klingenberg.groceriesapi.DTO.ProductDto;
import ru.klingenberg.groceriesapi.db.entity.Product;
import ru.klingenberg.groceriesapi.service.ProductService;

@RestController
@RequiredArgsConstructor
public class GroceriesController {

    private final ProductService productService;

    @PostMapping("/add")
    public Product addProduct(@RequestBody @Valid ProductDto productDto){
        return productService.save(productDto);
    }

}
