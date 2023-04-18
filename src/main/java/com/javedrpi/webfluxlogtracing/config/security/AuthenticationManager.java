package com.javedrpi.webfluxlogtracing.config.security;

import com.javedrpi.webfluxlogtracing.service.UserService;
import com.javedrpi.webfluxlogtracing.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.security.jwt.enabled", havingValue = "true")
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;
    private final UserService service;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getPrincipal().toString();
        String username = jwtUtil.extractUsername(authToken);
        return service.findUserByEmail(username)
                .filter(userDetails -> jwtUtil.validateToken(authToken, userDetails))
                .switchIfEmpty(Mono.empty())
                .map(userDetails -> {
//                    Claims claims = jwtUtil.extractClaim(authToken, c -> c);
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            userDetails.getAuthorities()
                    );
                });
    }
}
