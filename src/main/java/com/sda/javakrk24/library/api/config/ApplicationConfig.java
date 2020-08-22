package com.sda.javakrk24.library.api.config;

import com.sda.javakrk24.library.api.dao.BookEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
public class ApplicationConfig {

    //@Primary
    @Bean
    @Scope("prototype")
    //@Qualifier("prototype")
    public BookEntity bookEntityPrototype(){
        return new BookEntity();
    }

}
