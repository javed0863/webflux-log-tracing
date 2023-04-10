package com.javedrpi.webfluxlogtracing.controller;

import com.javedrpi.webfluxlogtracing.model.Quote;
import com.javedrpi.webfluxlogtracing.service.QuotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.List;
import java.util.random.RandomGenerator;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@RestController
@RequiredArgsConstructor
public class QuotesController {
    private final QuotesService quotesService;

    @GetMapping(value = "/quote", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Quote>> getQuote(){
        return quotesService.getQuote()
                .map(ResponseEntity::ok);
    }
}
