package br.com.gritti.infra.config;

import br.com.gritti.infra.security.AuthenticatedUserIdResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${cors.originPatterns}")
  private String corsOriginsPatterns;

  private final AuthenticatedUserIdResolver authenticatedUserIdResolver;

  @Autowired
  public WebConfig(AuthenticatedUserIdResolver authenticatedUserIdResolver) {
    this.authenticatedUserIdResolver = authenticatedUserIdResolver;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(authenticatedUserIdResolver);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    var allowedOrigins = corsOriginsPatterns.split(",");
    registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins(allowedOrigins)
            .allowCredentials(false);
  }
}
