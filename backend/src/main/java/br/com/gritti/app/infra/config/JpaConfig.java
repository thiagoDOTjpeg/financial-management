package br.com.gritti.app.infra.config;

import br.com.gritti.app.infra.security.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class JpaConfig {
  @Bean
  public AuditorAware<String> auditorAware() { return new AuditorAwareImpl(); }
}
