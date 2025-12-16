package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para el microservicio de Receipts
 * Proporciona documentación interactiva de la API REST
 * 
 * @author Equipo Amaterasu
 * @version 1.0.0
 */

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Receipt Service API")
                        .version("1.0.0")
                        .description("API para gestión de recibos en el sistema ECIEXPRESS"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8082")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://api.eciexpress.com")
                                .description("Servidor de Producción")
                ));
    }
}