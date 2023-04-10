package com.javedrpi.webfluxlogtracing.config;

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
    @Bean
    WebFilter traceIdInResponseFilter(Tracer tracer) {
        //            @Observed
        return (exchange, chain) -> {
            Span currentSpan = tracer.currentSpan();
            if (currentSpan != null) {
                // putting trace id value in [traceId] response header
                exchange.getResponse().getHeaders().add("traceId", currentSpan.context().traceId());
            }
            return chain.filter(exchange);
        };
    }
}
