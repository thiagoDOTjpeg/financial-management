package br.com.gritti.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class OpenApiConfig {

  @Bean
  OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Financial Management API")
                    .version("v1")
                    .description("Project for Financial Management")
                    .termsOfService("https://github.com/thiagoDOTjpeg")
                    .license(new License()
                            .name("Apache 2.0")
                            .url("https://github.com/thiagoDOTjpeg")));
  }

}
