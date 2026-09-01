package com.mihirjha.cloudai.queue.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI queueAdvisoryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Queue Advisory System API")
                        .description(
                                "API for analyzing queue images and generating "
                                        + "AI-powered queue advisory."
                        )
                        .version("v1"));
    }
}