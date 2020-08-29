package com.sda.javakrk24.library.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@Slf4j
public abstract class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected PasswordEncoder encoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected UserDetails getAppUser(){
        return User.builder()
                .username("user@user.pl")
                .password(encoder.encode("123"))
                .roles("API_USER")
                .build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator", "/console*/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/**").hasRole("API_USER")
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole("API_USER")
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("API_USER")
                .antMatchers("/hello", "/hello*").hasRole("HELLO_USER")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .httpBasic()
                .and()
            .headers()
                .frameOptions()
                .sameOrigin()
                .and()
            .csrf()
                .disable();
    }
}
