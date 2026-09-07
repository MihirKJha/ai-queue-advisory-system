package com.mihirjha.cloudai.queue.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Jackson {@link ObjectMapper} used by the application.
 *
 * <p>
 * The mapper is exposed as a Spring bean so application components and
 * cloud adapters can use a single, centrally managed JSON serialization
 * configuration.
 * </p>
 */
@Configuration
public class JacksonConfig {

    /**
     * Creates the application's JSON object mapper.
     *
     * @return configured Jackson object mapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}