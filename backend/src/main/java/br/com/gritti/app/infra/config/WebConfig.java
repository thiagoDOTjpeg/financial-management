package br.com.gritti.app.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${cors.originPatterns}")
  private String corsOriginsPatterns;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    var allowedOrigins = corsOriginsPatterns.split(",");
    registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins(allowedOrigins)
            .allowCredentials(false);
  }
}
