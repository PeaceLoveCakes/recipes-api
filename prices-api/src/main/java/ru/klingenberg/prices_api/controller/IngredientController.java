package ru.klingenberg.prices_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.klingenberg.prices_api.dto.IngredientDto;
import ru.klingenberg.prices_api.service.IngredientProductsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientProductsService ingredientService;

    @PostMapping("/options/{id}")
    public IngredientDto setOptions(@PathVariable String id,
                                    @RequestBody List<String> productIds){
        return ingredientService.setOptions(id, productIds);
    }

}