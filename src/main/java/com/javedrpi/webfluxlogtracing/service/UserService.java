package com.javedrpi.webfluxlogtracing.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    /**
     * Simulating response received from a DB call or LDAP call to get {@link UserDetails}
     */
    private final static List<UserDetails> APP_USERS = Arrays.asList(
            new User("john@gmail.com", "pass", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
            new User("jane@gmail.com", "pass", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
    );

    public Mono<UserDetails> findUserByEmail(String username) throws UsernameNotFoundException {
        return  Mono.justOrEmpty(
                APP_USERS.parallelStream()
                        .filter(u -> u.getUsername().equals(username))
                        .map(u -> new User(u.getUsername(), passwordEncoder.encode(u.getPassword()), u.getAuthorities()))
                        .findFirst()
        );
    }

}
