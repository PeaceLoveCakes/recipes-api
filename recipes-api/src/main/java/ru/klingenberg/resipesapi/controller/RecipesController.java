package ru.klingenberg.resipesapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.klingenberg.resipesapi.db.entity.Ingredient;
import ru.klingenberg.resipesapi.db.entity.Recipe;
import ru.klingenberg.resipesapi.dto.RecipeDto;
import ru.klingenberg.resipesapi.service.RecipeService;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Tag(name="Recipes")
public class RecipesController {

    private final RecipeService recipeService;

    @PostMapping
    public RecipeDto save(@RequestBody RecipeDto recipeDto){
        return recipeService.save(recipeDto);
    }

    @Operation(
            summary = "Find recipe by id"
    )
    @GetMapping("/{id}")
    public RecipeDto findById(@PathVariable String id){
        return recipeService.findById(id);
    }

    @GetMapping("/all")
    public Page<RecipeDto> all(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "36", required = false) int pageSize) {
        return recipeService.findAll(pageNo, pageSize);
    }

    @GetMapping("/search")
    public Page<RecipeDto> findByName(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "36", required = false) int pageSize,
            @RequestParam String name){
        return recipeService.searchByName(name, pageNo, pageSize);
    }

    @PutMapping
    public RecipeDto editByIdById(@RequestBody RecipeDto recipeDto){
        return recipeService.save(recipeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        recipeService.deleteById(id);
    }
}
