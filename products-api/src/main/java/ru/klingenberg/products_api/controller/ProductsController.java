package ru.klingenberg.products_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.klingenberg.products_api.db.entity.Product;
import ru.klingenberg.products_api.dto.PageableResponse;
import ru.klingenberg.products_api.dto.ProductDto;
import ru.klingenberg.products_api.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
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
