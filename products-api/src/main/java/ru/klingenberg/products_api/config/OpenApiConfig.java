package ru.klingenberg.products_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Products Api",
                description = "Products from different shops, with prices", version = "1.0.0"
        )
)
public class OpenApiConfig {

}
