package ru.klingenberg.resipesapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.klingenberg.resipesapi.db.entity.*;
import ru.klingenberg.resipesapi.db.repository.RecipeIngredientRepository;
import ru.klingenberg.resipesapi.db.repository.RecipeRepository;
import ru.klingenberg.resipesapi.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final RecipeIngredientRepository recipeIngredientRepository;

    private RecipeDto recipeDtoFrom(Recipe recipe) {
        return new RecipeDto()
                .setDescription(recipe.getDescription())
                .setName(recipe.getName())
                .setId(recipe.getId())
                .setIngredients(getIngredientsFrom(recipe))
                .setSteps(recipe.getSteps().stream()
                        .map(StepDto::from)
                        .collect(Collectors.toList()));
    }

    private List<RecipeIngredientDto> getIngredientsFrom(Recipe recipe) {
        if (recipe.getRecipeIngredients() == null || recipe.getRecipeIngredients().isEmpty()) return new ArrayList<>();
        return recipe.getRecipeIngredients()
                .stream().map(recipeIngredient -> new RecipeIngredientDto()
                                    .setIngredientId(recipeIngredient.getIngredient().getId())
                                    .setName(recipeIngredient.getIngredient().getName())
                        .setAmount(recipeIngredient.getAmount())
                        .setMeasurementUnit(recipeIngredient.getMeasurementUnit())
                ).collect(Collectors.toList());
    }

    private Recipe recipeFrom(RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(recipeDto.getId())
                .orElseGet(() -> new Recipe()
                                .setDescription(recipeDto.getDescription())
                                .setName(recipeDto.getName()));
        recipe.setSteps(recipeDto.getSteps().stream()
                        .map(stepDto -> new Step()
                                .setId(stepDto.getId())
                                .setText(stepDto.getText())
                                .setName(stepDto.getName())
                                .setRecipe(recipe))
                        .collect(Collectors.toList()))
                .setRecipeIngredients(recipeDto.getIngredients().stream()
                        .map(recipeIngredientDto -> recipeIngredientFromDto(recipeIngredientDto, recipe))
                        .collect(Collectors.toList()));
        return recipe;
    }

    private RecipeIngredient recipeIngredientFromDto(RecipeIngredientDto recipeIngredientDto, Recipe recipe) {
        Ingredient ingredient = ingredientService.findByIdOrName(recipeIngredientDto.getIngredientId(), recipeIngredientDto.getName())
                .orElseGet(() -> ingredientService.save(new IngredientDto().setName(recipeIngredientDto.getName())));
        RecipeIngredient recipeIngredient = recipeIngredientRepository
                .findByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId())
                .orElseGet(() -> new RecipeIngredient().setIngredient(ingredient));
        return recipeIngredient
                .setAmount(recipeIngredientDto.getAmount())
                .setMeasurementUnit(recipeIngredientDto.getMeasurementUnit())
                .setRecipe(recipe);
    }

    public RecipeDto findById(String id) {
        return recipeDtoFrom(recipeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    public RecipeDto save(RecipeDto recipeDto) {
        return recipeDtoFrom(recipeRepository.save(recipeFrom(recipeDto)));
    }

    public Page<RecipeDto> findAll(int pageNo, int pageSize) {
        return recipeRepository.findAll(PageRequest.of(pageNo, pageSize)).map(this::recipeDtoFrom);
    }

    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }

    public Page<RecipeDto> searchByName(String name, int pageNo, int pageSize) {
        return recipeRepository.findByNameContainingIgnoreCase(name, PageRequest.of(pageNo,pageSize)).map(this::recipeDtoFrom);
    }
}
