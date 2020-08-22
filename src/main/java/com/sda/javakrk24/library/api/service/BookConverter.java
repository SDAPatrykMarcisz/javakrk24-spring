package com.sda.javakrk24.library.api.service;

import com.sda.javakrk24.library.api.dao.BookEntity;
import com.sda.javakrk24.library.api.dto.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {



    public BookEntity fromDto(Book book){
        BookEntity entity = new BookEntity();
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPages(book.getPages());
        entity.setPublishYear(book.getPublishYear());
        entity.setIsbn(book.getIsbn());
        return entity;
    }

}
