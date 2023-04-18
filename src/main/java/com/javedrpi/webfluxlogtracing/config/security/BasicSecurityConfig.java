package com.javedrpi.webfluxlogtracing.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.security.basic.enabled", havingValue = "true")
public class BasicSecurityConfig {

    @Bean
    public SecurityWebFilterChain basicSecurityWebFilterChain(ServerHttpSecurity httpSecurity){
        httpSecurity
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/swagger**", "/webjars/**", "/v3/api-docs/**", "/actuator/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .httpBasic(withDefaults());

                //.formLogin();
        return httpSecurity.build();
    }

}
