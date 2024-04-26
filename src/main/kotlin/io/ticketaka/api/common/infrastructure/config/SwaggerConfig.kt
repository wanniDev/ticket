package io.ticketaka.api.common.infrastructure.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info =
        io.swagger.v3.oas.annotations.info.Info(
            title = "ticketaka",
            version = "0.0.1",
            description = "ticketaka API specification",
        ),
)
@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val securityScheme =
            SecurityScheme()
                .name("bearer-jwt")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        val securityRequirement = SecurityRequirement().addList("bearer-jwt")
        val components =
            Components()
                .addSecuritySchemes("bearer-jwt", securityScheme)

        return OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}
