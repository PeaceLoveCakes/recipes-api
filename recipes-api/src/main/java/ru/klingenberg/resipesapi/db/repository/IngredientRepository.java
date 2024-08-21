package ru.klingenberg.resipesapi.db.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.klingenberg.resipesapi.db.entity.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

    @NonNull Page<Ingredient> findAll(@NonNull Pageable pageable);

    Optional<Ingredient> findByName(String name);

    Page<Ingredient> findByNameContainingIgnoreCase(String s, Pageable pageable);

    boolean existsByName(String name);

    Optional<Ingredient> findFirstByIdOrNameIgnoreCase(String id, String name);

    Optional<Ingredient> findByNameIgnoreCase(String trim);
}
