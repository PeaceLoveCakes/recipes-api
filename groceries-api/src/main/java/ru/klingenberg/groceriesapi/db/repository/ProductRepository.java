package ru.klingenberg.groceriesapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klingenberg.groceriesapi.db.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
