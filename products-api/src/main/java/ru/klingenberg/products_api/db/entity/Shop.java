package ru.klingenberg.products_api.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "shop")
@Getter
@Setter
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Product> products;

}
