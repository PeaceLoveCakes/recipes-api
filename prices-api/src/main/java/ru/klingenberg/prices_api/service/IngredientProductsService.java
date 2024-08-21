package ru.klingenberg.prices_api.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.klingenberg.prices_api.db.entity.IngredientProducts;
import ru.klingenberg.prices_api.db.repository.IngredientProductsRepository;
import ru.klingenberg.prices_api.dto.IngredientDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@RequiredArgsConstructor
public class IngredientProductsService {

    @Value("${urls.recipes-api}")
    private String recipesApiUrl;

    private final IngredientProductsRepository ingredientProductsRepository;
    private final ProductService productService;

    public IngredientDto setOptions(@NonNull String ingredientId, List<String> productIds) {
        return ingredientDtoFrom(
                ingredientProductsRepository.save(ingredientProductsRepository.findById(ingredientId)
                        .orElseGet(() -> new IngredientProducts().setIngredientId(ingredientId))
                        .setProductIds(productIds)));
    }

    public IngredientDto get(String id) {
        return ingredientDtoFrom(ingredientProductsRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    public IngredientDto get(String id, Double amount) {
        return ingredientDtoFrom(ingredientProductsRepository.findById(id)
                .orElseGet(() -> {
                            fetchIngredient(id);
                            return ingredientProductsRepository.save(new IngredientProducts().setIngredientId(id));
                        }
                ));
    }

    public IngredientDto ingredientDtoFrom(IngredientProducts ingredientProducts) {
        IngredientDto ingredientDto = fetchIngredient(ingredientProducts.getIngredientId());
        ingredientDto.setOptions(Optional.ofNullable(ingredientProducts.getProductIds())
                .orElse(new ArrayList<>())
                .stream().map(productService::fetchProduct)
                .collect(Collectors.toList()));
        return ingredientDto;
    }

    public IngredientDto fetchIngredient(String id) {
        return RestClient.builder()
                .baseUrl(String.format("%s%s%s", recipesApiUrl, "/ingredient/", id))
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build()
                .get()
                .retrieve()
                .toEntity(IngredientDto.class)
                .getBody();
    }

    public IngredientDto getWithOptions(IngredientDto ingredientDto) {
        IngredientProducts ingredientProducts = ingredientProductsRepository.findById(ingredientDto.getId())
                .orElseGet(() -> {
                    fetchIngredient(ingredientDto.getId());
                    return ingredientProductsRepository.save(new IngredientProducts().setIngredientId(ingredientDto.getId()));
                }
        );
        ingredientDto.setOptions(Optional.ofNullable(ingredientProducts.getProductIds())
                        .orElse(new ArrayList<>())
                .stream().map(productService::fetchProduct)
                .map(productDto -> productDto.calculateForAmount(ingredientDto.getAmount()))
                .collect(Collectors.toList()));
        return ingredientDto;
    }
}
