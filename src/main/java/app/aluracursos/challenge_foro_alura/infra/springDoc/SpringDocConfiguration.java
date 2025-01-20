package app.aluracursos.challenge_foro_alura.infra.springDoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
       .components(new Components()
       .addSecuritySchemes("bearer-key",
        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                .title("Challenge Foro Alura Hub")
                .description("""
                        Aplicación web que simula un foro.
                        """)
                .contact(new Contact()
                    .name("Nahuel B.Miras")
                    .email("nahuelbmiras@gmail.com")));
    }


}
