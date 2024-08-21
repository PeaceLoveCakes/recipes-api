package ru.klingenberg.prices_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.klingenberg.prices_api.dto.RecipeDto;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    @Value("${urls.recipes-api}")
    private String recipesApiUrl;

    private final IngredientProductsService ingredientProductsService;

    public RecipeDto getById(String id) {
        RecipeDto recipeDto = fetchRecipe(id);
        recipeDto.setIngredients(recipeDto.getIngredients().stream()
                .map(ingredientProductsService::getWithOptions)
                .collect(Collectors.toList()));
        return recipeDto;
    }

    private RecipeDto fetchRecipe(String id){
        return RestClient.builder()
                .baseUrl(String.format("%s%s%s", recipesApiUrl, "/recipe/", id))
                .messageConverters(httpMessageConverters ->
                        httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build()
                .get()
                .retrieve()
                .toEntity(RecipeDto.class)
                .getBody();
    }

}
