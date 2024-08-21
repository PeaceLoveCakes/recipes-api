package ru.klingenberg.resipesapi.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.klingenberg.resipesapi.dto.MeasurementUnit;

@Entity
@Getter
@Setter
@Table(name = "recipe_ingredient",
        uniqueConstraints = { @UniqueConstraint(columnNames =
                { "recipe_id", "ingredient_id" }) })
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private MeasurementUnit measurementUnit;

}
