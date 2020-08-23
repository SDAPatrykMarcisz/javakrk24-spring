package com.sda.javakrk24.library.api.external.google.books;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GoogleBooksApiConfig {

    @Bean
    @Qualifier("googleBooksApiRestTemplate")
    public RestTemplate googleBooksApiRestTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

}
