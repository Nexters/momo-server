package com.nexters.momo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Momo API",
                description = "Momo server api docs"
        )
)
@Configuration
public class SwaggerConfig { }
