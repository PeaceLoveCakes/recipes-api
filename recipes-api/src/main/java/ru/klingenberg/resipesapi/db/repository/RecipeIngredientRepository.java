package ru.klingenberg.resipesapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klingenberg.resipesapi.db.entity.RecipeIngredient;

import java.util.Optional;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, String> {
    Optional<RecipeIngredient> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
}