package ru.klingenberg.prices_api.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klingenberg.prices_api.db.entity.IngredientProducts;

@Repository
public interface IngredientProductsRepository extends JpaRepository<IngredientProducts, String> {
}
