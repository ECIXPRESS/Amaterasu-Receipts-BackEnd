package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    /**
     * Configuración personalizada de OpenAPI para el servicio de recibos
     * 
     * @return Configuración OpenAPI con información del servicio
     */
    @Bean
    public OpenAPI receiptsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Receipts Service API - ECIEXPRESS")
                        .description("API REST para la gestión de recibos y pagos en el sistema ECIEXPRESS. "
                                + "Este microservicio permite crear, consultar, actualizar y eliminar recibos de transacciones, "
                                + "así como gestionar el estado de los pagos realizados por los usuarios."))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo Local")
                ));
    }
}
