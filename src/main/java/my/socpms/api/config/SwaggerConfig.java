package my.socpms.api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "SOC Paperwork Management System API",
    description = "RESTful API for managing academic paperwork",
    version = "1.0.0"
))
public class SwaggerConfig {
    
    @Bean
    public org.springdoc.core.models.GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}
