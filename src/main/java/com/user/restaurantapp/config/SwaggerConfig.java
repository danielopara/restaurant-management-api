package com.user.restaurantapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Value("${base-url}")
    private String baseUrl;
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(Collections.singletonList(new Server()
                        .url(baseUrl)
                        .description("Server")));
    }

    private Info apiInfo(){
        return new Info()
                .title("Restaurant API")
                .description("Restaurant api.")
                .version("1.0");
    }
}
