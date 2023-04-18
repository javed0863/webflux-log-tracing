package com.javedrpi.webfluxlogtracing.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.security.jwt.enabled", havingValue = "true")
public class JwtSecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain jwtSecurityWebFilterChain(ServerHttpSecurity httpSecurity){
        httpSecurity
                .csrf().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/swagger**", "/webjars/**", "/v3/api-docs/**", "/actuator/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/login").permitAll()
                .anyExchange()
                .authenticated();

        //.formLogin();
        return httpSecurity.build();
    }
}
