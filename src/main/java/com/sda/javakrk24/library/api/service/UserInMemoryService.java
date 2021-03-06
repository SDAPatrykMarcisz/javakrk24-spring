package com.sda.javakrk24.library.api.service;

import com.sda.javakrk24.library.api.config.InMemoryAuthorizationConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@ConditionalOnBean(InMemoryAuthorizationConfig.class)
public class UserInMemoryService implements UserService {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final List<String> requestedUserCreations;

    public UserInMemoryService(InMemoryUserDetailsManager inMemoryUserDetailsManager, PasswordEncoder passwordEncoder) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
        requestedUserCreations = new LinkedList<>();
    }

    @Override
    public void addUser(String email, String password) {
        inMemoryUserDetailsManager.createUser(
                User.builder()
                        .username(email)
                        .password(passwordEncoder.encode(password))
                        .roles()
                        .build()
        );
        requestedUserCreations.add(email);
    }
}
