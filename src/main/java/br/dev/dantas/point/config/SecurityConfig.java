package br.dev.dantas.point.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] WHITE_LIST = {"/swagger-ui/index.html", "/v3/api-docs/**","/swagger-ui/**"};

  private static final String ADMIN = "ADMIN";

  @Bean
  SecurityFilterChain securiyFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth -> auth
                .requestMatchers(WHITE_LIST).permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/animes").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.PUT, "/api/v1/producers/").hasAuthority(ADMIN)
                .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .build();
  }
}
