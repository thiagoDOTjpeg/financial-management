package br.com.gritti.infra.config;

import br.com.gritti.infra.security.jwt.TokenFilter;
import br.com.gritti.infra.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
  private final TokenProvider tokenProvider;

  @Autowired
  public SecurityConfig(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Bean
  AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    TokenFilter tokenFilter = new TokenFilter(tokenProvider);
    return http.httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                    .requestMatchers("/**").permitAll())
//            .requestMatchers("/api/v1/auth/signin", "/api/v1/auth/refresh", "/swagger-ui/**", "/api-docs/**", "/api-docs", "/swagger-ui.html").permitAll()
//            .requestMatchers("/api/v1/users").permitAll()
//            .requestMatchers("/api/**").denyAll()
//            .requestMatchers("/users").denyAll())
            .cors(cors -> {})
            .build();
  }
}
