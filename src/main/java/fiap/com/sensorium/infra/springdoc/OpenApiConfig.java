package fiap.com.sensorium.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI sensoriumOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Os Tres Motoqueiros do Apocalipse Verde - API")
                        .description("API documentation for FIAP ADS 2025 'challenge' project")
                        .version("v0.5"));
    }
}
