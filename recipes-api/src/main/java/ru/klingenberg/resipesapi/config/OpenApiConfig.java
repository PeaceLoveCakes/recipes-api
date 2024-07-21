package ru.klingenberg.resipesapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Recipes Api",
                description = "Recipes and ingredients", version = "1.0.0"
        )
)
public class OpenApiConfig {

}
