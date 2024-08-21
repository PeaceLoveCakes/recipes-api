package ru.klingenberg.resipesapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.klingenberg.resipesapi.db.entity.Ingredient;

@Getter
@Setter
@ToString
public class IngredientDto {

    private String id;

    @NotBlank(message = "name required")
    private String name;

    public static IngredientDto from(Ingredient ingredient){
        return new IngredientDto()
                .setId(ingredient.getId())
                .setName(ingredient.getName());
    }
}
