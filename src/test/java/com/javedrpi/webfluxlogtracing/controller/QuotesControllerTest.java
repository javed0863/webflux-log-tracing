package com.javedrpi.webfluxlogtracing.controller;

import com.javedrpi.webfluxlogtracing.model.Quote;
import com.javedrpi.webfluxlogtracing.service.QuotesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest
//@Import(SecurityConfig.class)
@ContextConfiguration(classes = QuotesController.class)
class QuotesControllerTest {
    @MockBean
    private QuotesService quotesService;

    @Autowired
    private WebTestClient client;

    @Test
    void testGetQuote() {
        Mockito.when(quotesService.getQuote())
                .thenReturn(Mono.just(new Quote("Coding like poetry should be short and concise",
                        "Santosh Kalwar")));
        client
                .mutateWith(SecurityMockServerConfigurers.mockUser())
                .get()
                .uri("/quote")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Quote.class);
    }
}