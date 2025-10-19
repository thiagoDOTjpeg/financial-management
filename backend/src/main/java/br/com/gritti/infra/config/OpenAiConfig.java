package br.com.gritti.infra.config;

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
    contact.setEmail("tgritti.dev@gmail.com");

    Info information = new Info()
            .title("Monedo")
            .version("1.0.0")
            .description("""
                    Um sistema abrangente de gerenciamento financeiro que fornece autenticação de usuário,\s
                    gerenciamento de contas, rastreamento de transações, faturamento e recursos de categorização.""")
            .contact(contact);

    return new OpenAPI().info(information).servers(List.of(server));
  }
}
