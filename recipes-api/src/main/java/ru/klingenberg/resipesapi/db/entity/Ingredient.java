package ru.klingenberg.resipesapi.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.klingenberg.resipesapi.dto.IngredientDto;

import java.time.LocalDateTime;

@Entity(name = "ingredient")
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String name;

    public static Ingredient from(IngredientDto ingredientDto){
        return new Ingredient()
                .setId(ingredientDto.getId())
                .setName(ingredientDto.getName());
    }

}
