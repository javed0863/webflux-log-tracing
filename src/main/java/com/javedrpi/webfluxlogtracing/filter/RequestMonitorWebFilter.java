package com.javedrpi.webfluxlogtracing.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.context.ContextSnapshot;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Slf4j
@Component
public class RequestMonitorWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();
        return chain.filter(exchange)
                /**
                 * Preparing context for the Tracer Span used in TracerConfiguration
                 */
                .contextWrite(context -> {
                    ContextSnapshot.setThreadLocalsFrom(context, ObservationThreadLocalAccessor.KEY);
                    return context;
                })
                .doFinally(signalType -> {
                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;
                    /**
                     * Extracting traceId added in TracerConfiguration Webfilter bean
                     */
                    List<String> traceIds = ofNullable(exchange.getResponse().getHeaders().get("traceId")).orElse(List.of());
                    MetricsLogTemplate metricsLogTemplate = new MetricsLogTemplate(
                            String.join(",", traceIds),
                            exchange.getLogPrefix().trim(),
                            executionTime
                    );
                    try {
                        log.info(new ObjectMapper().writeValueAsString(metricsLogTemplate));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    record MetricsLogTemplate(String traceId, String logPrefix, long executionTime){}
}
