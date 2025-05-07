package com.sergiohscl.basic_concept_springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConf {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .name("Authorization")  // Adicione essa linha
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
            .info(new Info()
                .title("REST API's RESTful from 0 with Java, Spring Boot")
                .version("v1")
                .description("REST API's RESTful from 0 with Java, Spring Boot")
                .termsOfService("https://github.com/sergiohscl/basic_concept_springboot")
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://github.com/sergiohscl/basic_concept_springboot")
                )
            );
    }
    
}
