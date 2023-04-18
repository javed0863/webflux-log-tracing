package com.javedrpi.webfluxlogtracing.controller;

import com.javedrpi.webfluxlogtracing.model.AuthReq;
import com.javedrpi.webfluxlogtracing.model.AuthRes;
import com.javedrpi.webfluxlogtracing.service.UserService;
import com.javedrpi.webfluxlogtracing.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    @PostMapping("/login")
    public Mono<ResponseEntity<AuthRes>> login(@RequestBody AuthReq authReq){
        return userService.findUserByEmail(authReq.username())
                .map(userDetails -> ResponseEntity.ok(new AuthRes(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
