package ru.klingenberg.prices_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klingenberg.prices_api.dto.RecipeDto;
import ru.klingenberg.prices_api.service.RecipeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipesService;

    @GetMapping("/{id}")
    public RecipeDto getRecipe(@PathVariable String id){
        return recipesService.getById(id);
    }

}
