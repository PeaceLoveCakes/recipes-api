package ru.klingenberg.resipesapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.klingenberg.resipesapi.DTO.PageableResponse;
import ru.klingenberg.resipesapi.DTO.ProductDto;
import ru.klingenberg.resipesapi.db.entity.Product;
import ru.klingenberg.resipesapi.db.repository.ProductRepository;
import ru.klingenberg.resipesapi.model.MeasurementUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(ProductDto productDto) {
        normalizeAmount(productDto);

        Product product = productRepository.findByName(productDto.getName())
                .orElse(new Product());

        return productRepository.save(product
                .setName(productDto.getName())
                .setPrice(productDto.getPrice())
                .setMeasurementUnit(productDto.getMeasurementUnit()));
    }

    //Calculating price for ONE kg,g,unit,etc
    private void normalizeAmount(ProductDto productDto){
        Double amount = productDto.getAmount();
        if(productDto.getMeasurementUnit().equals(MeasurementUnit.ml)){
            productDto.setMeasurementUnit(String.valueOf(MeasurementUnit.l));
            amount = amount/1000;
        }
        if(productDto.getMeasurementUnit().equals(MeasurementUnit.g)){
            productDto.setMeasurementUnit(MeasurementUnit.kg);
            amount = amount/1000;
        }
        if(amount != 1d){
            Double multiply = 1/amount;
            productDto.setAmount(amount*multiply);
            productDto.setPrice(Math.floor(productDto.getPrice()*multiply * 100) / 100);
        }
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public PageableResponse<Product> findAll(int pageNo, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Product> page = productRepository.findAll(pageRequest);
        return new PageableResponse<Product>()
                .setElements(page.getContent())
                .setPageNo(pageNo)
                .setPageSize(pageSize)
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }

    public List<Product> save(List<ProductDto> products) {
        List<Product> response = new ArrayList<>();
        for(ProductDto productDto : products){
            response.add(save(productDto));
        }
        return response;
    }

    public Page<Product> findByName(String name, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return productRepository.findByNameContainingIgnoreCase(name.trim(), pageRequest);
    }
}
