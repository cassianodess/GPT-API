package com.cassianodess.gptapi.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableWebSecurity
public class AuthConfigurations {

    @Autowired
    private SecurityFilter filter;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(cors -> cors.configure(http))
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
            auth.requestMatchers( "/api/auth/**").permitAll();
            auth.requestMatchers("/api/auth/activate/**").permitAll();
            auth.anyRequest().authenticated();
        })
        .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
        .components(new Components())
            .schemaRequirement("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
            .info(new Info().title("ChatGPT CRONE API")
            .description("Web API application for chatgpt clone PROG WEB 2023.1")
            .version("v1.0.0")
            .license(new License().name("MIT License").url("https://opensource.org/license/mit/")));
    }

}
