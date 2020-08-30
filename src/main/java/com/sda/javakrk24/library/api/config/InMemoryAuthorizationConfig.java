package com.sda.javakrk24.library.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//spring.profiles.active=authorization-in-memory

@Slf4j
@Configuration
@Profile("authorization-in-memory")
public class InMemoryAuthorizationConfig extends AbstractSecurityConfig {

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("authorization in memory configured ");
        UserDetails user = User.builder()
                .username("user@user.pl")
                .password(encoder.encode("123"))
                .roles("API_USER")
                .build();

        UserDetails helloUser = User.builder()
                .username("hello@user.pl")
                .password(encoder.encode("123"))
                .roles("HELLO_USER")
                .build();

        log.info(encoder.encode("123"));
        auth.userDetailsService(userDetailsManager);
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(helloUser);
//        auth.inMemoryAuthentication().withUser(user).withUser(helloUser);
    }

}
