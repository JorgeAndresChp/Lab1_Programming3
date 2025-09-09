package una.ac.cr.Lab1Progra3.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("Lab1 API")
                        .description("API REST para el Laboratorio 1 - Programación 3")
                        .version("v1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio")
                        .url("https://github.com/"));
    }
}
