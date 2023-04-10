package com.javedrpi.webfluxlogtracing.controller;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Configuration(proxyBeanMethods = false)
public class MyConfiguration {
    /*@Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }*/

    @Bean
    WebFilter traceIdInResponseFilter(Tracer tracer) {
        return new WebFilter() {
            @Override
//            @Observed
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                Span currentSpan = tracer.currentSpan();
                if (currentSpan != null) {
                    // putting trace id value in [mytraceid] response header
                    exchange.getResponse().getHeaders().add("traceId", currentSpan.context().traceId());
                }
                return chain.filter(exchange);
            }
        };
    }
}
