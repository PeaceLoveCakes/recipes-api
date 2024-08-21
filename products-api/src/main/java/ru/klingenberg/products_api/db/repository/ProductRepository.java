package ru.klingenberg.products_api.db.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klingenberg.products_api.db.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @NonNull Page<Product> findAll(@NonNull Pageable pageable);

    Optional<Product> findByName(String name);

    Page<Product> findByNameContainingIgnoreCase(String s, Pageable pageable);

    boolean existsByName(String name);

    Optional<Product> findByInShopIdAndShopId(String inShopId, String shopId);

}
