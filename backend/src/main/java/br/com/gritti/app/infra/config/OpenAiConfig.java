package br.com.gritti.app.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAiConfig {

  @Bean
  public OpenAPI defineOpenApi() {
    Server server = new Server();
    server.setUrl("http://localhost:8080/");
    server.setDescription("Development");

    Contact contact = new Contact();
    contact.setName("Thiago Gritti");
    contact.setEmail("thiago.gritti12@gmail.com");

    Info information = new Info()
            .title("Financial Management System")
            .version("1.0.0")
            .description("""
                    A comprehensive financial management system that provides user authentication,\s
                    account management, transaction tracking, billing, and categorization features.
                   \s""")
            .contact(contact);

    return new OpenAPI().info(information).servers(List.of(server));
  }
}
