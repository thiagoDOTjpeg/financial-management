package br.com.gritti.app.infra.config;

import br.com.gritti.app.infra.security.jwt.TokenFilter;
import br.com.gritti.app.infra.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
  @Autowired
  private TokenProvider tokenProvider;

  @Bean
  PasswordEncoder passwordEncoder() {
    Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("pbkdf2", encoder);
    DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
    passwordEncoder.setDefaultPasswordEncoderForMatches(encoder);
    return passwordEncoder;
  }

  @Bean
  AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    TokenFilter tokenFilter = new TokenFilter(tokenProvider);
    return http.httpBasic(AbstractHttpConfigurer::disable)
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint((request, response, authException) -> {
                      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                      response.setContentType("application/json");
                      response.getWriter().write("{\"timestamp\": \"" + new Date() + "\", \"error\":\"Unauthorized\", \"message\":\"Authentication required.\"}");
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                      response.setContentType("application/json");
                      response.getWriter().write("{\"timestamp\": \" \" + new Date() + \",\"error\":\"Unauthorized\", \"message\":\"Access denied.\"}");
                    }))
            .csrf(AbstractHttpConfigurer::disable).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                    .requestMatchers("/**").permitAll())
                    /*.requestMatchers("/api/v1/auth/signin", "/api/v1/auth/refresh", "/swagger-ui/**", "/api-docs/**", "/api-docs", "/swagger-ui.html").permitAll()
                    .requestMatchers("/api/v1/user").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers("/users").denyAll())*/
            .cors(cors -> {})
            .build();
  }
}
