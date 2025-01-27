package br.com.gritti.app.config;

import br.com.gritti.app.security.jwt.JwtTokenFilter;
import br.com.gritti.app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private JwtTokenProvider tokenProvider;

  @Bean
  PasswordEncoder passwordEncoder() {
    Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("pbkdf2", encoder);
    DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
    delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(encoder);
    return delegatingPasswordEncoder;
  }

  @Bean
  AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(tokenProvider);
    return http.httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                    .requestMatchers("/**").permitAll()
                    .requestMatchers("/h2-console", "/h2-console/**").permitAll())
            .cors(cors -> {})
            .build();

  }

}
