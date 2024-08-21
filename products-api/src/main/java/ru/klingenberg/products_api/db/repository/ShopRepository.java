package ru.klingenberg.products_api.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klingenberg.products_api.db.entity.Shop;

import java.util.Optional;


@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

    Optional<Shop> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String trim);
}
