package ru.klingenberg.resipesapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.klingenberg.resipesapi.db.entity.Product;
import ru.klingenberg.resipesapi.db.entity.Recipe;
import ru.klingenberg.resipesapi.dto.RecipeDtoGet;
import ru.klingenberg.resipesapi.dto.RecipeDtoPost;
import ru.klingenberg.resipesapi.service.RecipeService;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Tag(name="Recipes")
public class RecipesController {

    private final RecipeService recipeService;

    @PostMapping("/add")
    public RecipeDtoGet add(@RequestBody RecipeDtoPost recipeDto){
        return recipeService.save(recipeDto);
    }

    @Operation(
            summary = "Find recipe by id"
    )
    @GetMapping("/{id}")
    public RecipeDtoGet getById(@PathVariable String id){
        return recipeService.getById(id);
    }

    @GetMapping("/all")
    public Page<Recipe> all(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "36", required = false) int pageSize) {
        return recipeService.findAll(pageNo, pageSize);
    }

    @GetMapping("/search")
    public Page<Product> findByName(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "36", required = false) int pageSize,
            @RequestParam String name){
        return recipeService.findByName(name, pageNo, pageSize);
    }
}
