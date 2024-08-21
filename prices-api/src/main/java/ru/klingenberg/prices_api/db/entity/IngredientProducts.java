package ru.klingenberg.prices_api.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class IngredientProducts {

    @Id
    private String ingredientId;

    private List<String> productIds;
}
