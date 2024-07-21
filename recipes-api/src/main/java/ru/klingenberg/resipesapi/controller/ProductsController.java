package ru.klingenberg.resipesapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.klingenberg.resipesapi.dto.PageableResponse;
import ru.klingenberg.resipesapi.dto.ProductDto;
import ru.klingenberg.resipesapi.db.entity.Product;
import ru.klingenberg.resipesapi.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name="Ingredients")
public class ProductsController {

    private final ProductService productService;

    @PostMapping("/add")
    public Product addProduct(@RequestBody @Valid ProductDto productDto){
        return productService.save(productDto);
    }

    @PostMapping("/addAll")
    public List<Product> addProducts(@RequestBody List<ProductDto> products){
        return productService.save(products);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable String id){
        return productService.findById(id).orElse(null);
    }

    @GetMapping("/all")
    public PageableResponse<Product> findAll(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "36", required = false) int pageSize){
        return productService.findAll(pageNo, pageSize);
    }

    @GetMapping("/search")
    public Page<Product> findByName(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "36", required = false) int pageSize,
            @RequestParam String name){
        return productService.findByName(name, pageNo, pageSize);
    }

}
