package com.example.supportapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI supportApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Support API")
                        .version("1.0")
                        .description("API para la gestión de solicitudes, clientes y técnicos.")
                        .contact(new Contact()
                                .name("Josue Morales")
                                .email("josue.morales@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

