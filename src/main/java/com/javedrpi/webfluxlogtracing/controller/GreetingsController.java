package com.javedrpi.webfluxlogtracing.controller;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@RestController
@Slf4j
@RequiredArgsConstructor
public class GreetingsController {
//    private final ObservationRegistry registry;
//    @Observed
    @GetMapping("/hello")
    public Mono<ResponseEntity<String>> greet(@RequestParam String name){
        log.info("Greeting User...");
        return Mono.fromSupplier(() -> ResponseEntity.ok("Hello "+name));
//                .tap(Micrometer.observation(registry));
    }

//    @Observed(name = "user.name",
//            contextualName = "getting-user-name",
//            lowCardinalityKeyValues = {"userType", "userType2"})
    @PostMapping(value = "/helloTo/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> greet2(@PathVariable String name){
        log.info("Greeting User..."+name);
        return Mono.fromSupplier(() -> "{\"greet\": \""+name+"\"}")
                .delayElement(Duration.ofSeconds(5))
//                .tap(Micrometer.observation(registry))
                ;
    }
}
