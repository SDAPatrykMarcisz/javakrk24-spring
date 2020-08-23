package com.sda.javakrk24.library.api.external.google.books;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class GoogleBooksApiClient {

    private RestTemplate restTemplate;
    private String apiBaseUrl;

    @Autowired
    private GoogleBooksApiClient(
            @Qualifier("googleBooksApiRestTemplate") RestTemplate restTemplate,
            @Value("${external.api.google.books.base.url}") String apiBaseUrl) {

        this.restTemplate = restTemplate;
        this.apiBaseUrl = apiBaseUrl;
    }

    //https://www.googleapis.com/books/v1/   volumes?q=isbn:9780871293879

    public void getBookByIsbn(String isbn) {
        String url = String.format("%svolumes?q=isbn:%s", apiBaseUrl, isbn);

        ResponseEntity<String> exchange = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class
        );

        log.info(exchange.getBody());
    }

}
