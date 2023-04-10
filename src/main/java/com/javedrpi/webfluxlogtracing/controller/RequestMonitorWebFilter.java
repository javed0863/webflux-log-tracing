package com.javedrpi.webfluxlogtracing.controller;

import io.micrometer.context.ContextSnapshot;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Slf4j
@Component
public class RequestMonitorWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long st = System.currentTimeMillis();
        return chain.filter(exchange)
                .contextWrite(context -> {
                    /*var traceId = exchange.getLogPrefix();
                    Context context1 = context.put("traceId", traceId);
                    exchange.getAttributes().put("traceId", traceId);*/
                    ContextSnapshot.setThreadLocalsFrom(context, ObservationThreadLocalAccessor.KEY);
                    return context;
                })
                .doFinally(signalType -> {
                    long et = System.currentTimeMillis();
                    long executionTime = et-st;
                    log.info(exchange.getResponse().getHeaders().get("traceId")+" Total time: "+executionTime);
                });
    }
}
