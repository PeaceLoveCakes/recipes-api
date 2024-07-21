package ru.klingenberg.resipesapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.klingenberg.resipesapi.db.entity.Product;
import ru.klingenberg.resipesapi.db.entity.Recipe;
import ru.klingenberg.resipesapi.db.entity.RecipeProduct;
import ru.klingenberg.resipesapi.db.entity.Step;
import ru.klingenberg.resipesapi.db.repository.RecipeRepository;
import ru.klingenberg.resipesapi.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ProductService productService;

    private RecipeDtoGet recipeDtoFrom(Recipe recipe) {
        return new RecipeDtoGet()
                .setDescription(recipe.getDescription())
                .setName(recipe.getName())
                .setId(recipe.getId())
                .setProducts(getProductsFrom(recipe))
                .setSteps(recipe.getSteps().stream()
                        .map(StepDto::from)
                        .collect(Collectors.toList()));
    }

    private List<ProductDto> getProductsFrom(Recipe recipe) {
        if (recipe.getRecipeProducts() == null || recipe.getRecipeProducts().isEmpty()) return new ArrayList<>();
        return recipe.getRecipeProducts()
                .stream().map(recipeProduct -> {
//                            recipeProduct.getProduct().normalizeAmount();
                            return new ProductDto()
                                    .setId(recipeProduct.getProduct().getId())
                                    .setName(recipeProduct.getProduct().getName())
//                                    .setAmount(recipeProduct.getAmount() * recipeProduct.getAmount())
//                                    .setPrice(recipeProduct.getProduct().getPrice() * recipeProduct.getAmount())
                                    .setMeasurementUnit(recipeProduct.getProduct().getMeasurementUnit());
                        }
                ).collect(Collectors.toList());
    }

    private Recipe recipeFrom(RecipeDtoPost recipeDto) {
        Recipe recipe = new Recipe()
                .setDescription(recipeDto.getDescription())
                .setName(recipeDto.getName())
                .setSteps(recipeDto.getSteps().stream()
                        .map(stepDto -> new Step()
                                .setText(stepDto.getText())
                                .setName(stepDto.getName()))
                        .collect(Collectors.toList()));
        recipe
                .setRecipeProducts(recipeDto.getProductIds().stream()
                        .map(recipeProductDto -> {
                            Product product = productService.findById(recipeProductDto.getProductId())
                                    .orElseThrow();
                            return new RecipeProduct()
                                    .setProduct(product)
                                    .setAmount(recipeProductDto.getAmount());
                        }).collect(Collectors.toList()));
        return recipe;
    }

    public RecipeDtoGet getById(String id) {
        return recipeDtoFrom(recipeRepository.findById(id).orElseThrow());
    }

    public RecipeDtoGet save(RecipeDtoPost recipeDto) {
        return recipeDtoFrom(recipeRepository.save(recipeFrom(recipeDto)));
    }

    public Page<Recipe> findAll(int pageNo, int pageSize) {
        return recipeRepository.findAll(PageRequest.of(pageNo, pageSize));
    }

    public Page<Product> findByName(String name, int pageNo, int pageSize) {
        return recipeRepository.findByNameContainingIgnoreCase(name.trim(), PageRequest.of(pageNo, pageSize));
    }
}
