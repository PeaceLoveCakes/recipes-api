package ru.klingenberg.resipesapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.klingenberg.resipesapi.dto.PageableResponse;
import ru.klingenberg.resipesapi.dto.IngredientDto;
import ru.klingenberg.resipesapi.db.entity.Ingredient;
import ru.klingenberg.resipesapi.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
@Tag(name="Ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/add")
    public Ingredient addProduct(@RequestBody @Valid IngredientDto ingredientDto){
        return ingredientService.save(ingredientDto);
    }

    @PostMapping("/addAll")
    public List<Ingredient> addProducts(@RequestBody List<IngredientDto> products){
        return ingredientService.save(products);
    }

    @GetMapping("/{id}")
    public Ingredient getById(@PathVariable String id){
        return ingredientService.findById(id)
                .orElseThrow();
    }

    @GetMapping("/all")
    public PageableResponse<Ingredient> findAll(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "36", required = false) int pageSize){
        return ingredientService.findAll(pageNo, pageSize);
    }

    @GetMapping("/search")
    public Page<Ingredient> findByName(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "36", required = false) int pageSize,
            @RequestParam String name){
        return ingredientService.searchByName(name, pageNo, pageSize);
    }

}
