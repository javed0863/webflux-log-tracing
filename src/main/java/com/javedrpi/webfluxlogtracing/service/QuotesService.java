package com.javedrpi.webfluxlogtracing.service;

import com.javedrpi.webfluxlogtracing.model.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.List;
import java.util.random.RandomGenerator;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Slf4j
@Service
public class QuotesService {
    private static List<Quote> quotes = List.of(
            new Quote("Coding like poetry should be short and concise", "Santosh Kalwar"),
            new Quote("First, solve the problem. Then, write the code", "John Johnson"),
            new Quote("Code is like humor. When you have to explain it, it’s bad", "Cory House"),
            new Quote("Clean code always looks like it was written by someone who care", "Robert C. Martin"),
            new Quote("Programming today is a race between software engineers striving to build bigger and better idiot-proof programs and the Universe trying to produce bigger and better idiots. So far, the Universe is winning", "Rick Cook"),
            new Quote("Any fool can write code that a computer can understand. Good programmers write code that humans can understand", "Martin Fowler"),
            new Quote("Software is like sex: it’s better when it’s free", "Linus Torvalds"),
            new Quote("If debugging is the process of removing bugs, then programming must be the process of putting them in", "Sam Redwine"),
            new Quote("If, at first, you do not succeed, call it version 1.0", "Khayri R.R. Woulfe"),
            new Quote("Computers are fast; developers keep them slow", "Anonymous")
    );
    public Mono<Quote> getQuote(){
        RandomGenerator randomGenerator = new SecureRandom();
        int i = randomGenerator.nextInt(0, quotes.size()-1);
        Quote quote = quotes.get(i);
        log.info(quote.toString());
        return Mono.just(quote);
    }
}
