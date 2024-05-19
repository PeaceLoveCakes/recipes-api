package ru.klingenberg.groceriesapi.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klingenberg.groceriesapi.DTO.ProductDto;
import ru.klingenberg.groceriesapi.db.entity.Product;
import ru.klingenberg.groceriesapi.db.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(ProductDto productDto) {
        return productRepository.save(new Product()
                .setName(productDto.getName())
                .setPrice(productDto.getPrice())
                .setAmount(productDto.getAmount()));
    }
}
