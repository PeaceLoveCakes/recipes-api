package ru.klingenberg.products_api.db.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.klingenberg.products_api.db.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    @NonNull Page<Product> findAll(@NonNull Pageable pageable);

    Optional<Product> findByName(String name);

    Page<Product> findByNameContainingIgnoreCase(String s, Pageable pageable);

//    Page<Product> findByShopIdAndInShopId(String shopId, String inShopId);

    boolean existsByName(String name);
}
