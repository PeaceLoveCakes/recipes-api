package ru.klingenberg.products_api.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.klingenberg.products_api.db.entity.Product;
import ru.klingenberg.products_api.db.entity.Shop;
import ru.klingenberg.products_api.db.repository.ProductRepository;
import ru.klingenberg.products_api.dto.MeasurementUnit;
import ru.klingenberg.products_api.dto.PageableResponse;
import ru.klingenberg.products_api.dto.ProductDto;
import ru.klingenberg.products_api.dto.ShopProducts;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ShopService shopService;

    public Product save(ProductDto productDto) {
        return productRepository.save(new Product()
                        .setName(productDto.getName())
                        .setPrice(productDto.getPrice())
                        .setAmount(productDto.getAmount())
                        .setMeasurementUnit(productDto.getMeasurementUnit()));
    }

    public Product save(Product product, @NonNull String shopId){
        productRepository.findByInShopIdAndShopId(product.getInShopId(), shopId)
                .ifPresent(product1 -> product.setId(product1.getId()));
        return productRepository.save(product);
    }

    //Calculate price for ONE kg,g,unit,etc
    public void normalizeAmount(ProductDto productDto) {
        Double amount = productDto.getAmount();
        if (productDto.getMeasurementUnit().equals(MeasurementUnit.ml)) {
            productDto.setMeasurementUnit(String.valueOf(MeasurementUnit.l));
            amount = amount / 1000;
        }
        if (productDto.getMeasurementUnit().equals(MeasurementUnit.g)) {
            productDto.setMeasurementUnit(MeasurementUnit.kg);
            amount = amount / 1000;
        }
        if (amount != 1d) {
            Double multiply = 1 / amount;
            productDto.setAmount(amount * multiply);
            productDto.setPrice(Math.floor(productDto.getPrice() * multiply * 100) / 100);
        }
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public PageableResponse<ProductDto> findAll(int pageNo, int pageSize) {
        Page<Product> page = productRepository.findAll(PageRequest.of(pageNo, pageSize));
        return new PageableResponse<ProductDto>()
                .setElements(page.getContent().stream().map(ProductDto::from).collect(Collectors.toList()))
                .setPageNo(pageNo)
                .setPageSize(pageSize)
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages())
                .setHasNext(page.hasNext());
    }

    public List<Product> save(List<ProductDto> products) {
        return productRepository.saveAll(products.stream()
                .map(Product::from)
                .collect(Collectors.toList()));
    }

    public Page<ProductDto> searchByName(String name, int pageNo, int pageSize) {
        return productRepository.findByNameContainingIgnoreCase(name.trim(), PageRequest.of(pageNo, pageSize))
                .map(ProductDto::from);
    }

    public void save(ShopProducts products) {
        Shop shop = shopService.getOrCreateByName(products.getShopName());
        products.getElements().stream()
                .map(productDto -> Product.from(productDto).setShop(shop))
                .forEach(product -> save(product, shop.getId()));
    }
}
