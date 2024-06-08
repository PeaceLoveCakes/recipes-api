package ru.klingenberg.resipesapi.db.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.klingenberg.resipesapi.db.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @NonNull Page<Product> findAll(@NonNull Pageable pageable);

    Optional<Product> findByName(String name);

    Page<Product> findByNameContainingIgnoreCase(String s, Pageable pageable);
}
