package com.pritam.journalApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Journal App API")
                          .version("v1.0")
                        .description("by Pritam")
        );
    }
}
//http://localhost:8080/journal/swagger-ui/index.html#/