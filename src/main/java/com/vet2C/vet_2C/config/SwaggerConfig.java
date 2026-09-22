package com.vet2C.vet_2C.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI vet2COpenApi(){
        return new OpenAPI()
                .info(new Info().title("Api Clinica veterinaria")
                        .description("Documentacion de la api clinica veterinaria 2C")
                .version("1.0")
                .contact(new Contact().name("Maximiliano Avendaño")
                        .email("maximilianoariela@gmail.com"))
                        .license(new License().name("Uso Academico"))
                );
    }
}
