package ru.klingenberg.resipesapi.db.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.klingenberg.resipesapi.db.entity.Product;
import ru.klingenberg.resipesapi.db.entity.Recipe;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    @EntityGraph(attributePaths = {"recipeProducts.product", "steps"})
    @NonNull
    Optional<Recipe> findById(@NonNull String id);

    Page<Product> findByNameContainingIgnoreCase(String trim, PageRequest of);
}
