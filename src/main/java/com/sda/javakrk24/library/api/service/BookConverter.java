package com.sda.javakrk24.library.api.service;

import com.sda.javakrk24.library.api.dao.BookEntity;
import com.sda.javakrk24.library.api.dto.Author;
import com.sda.javakrk24.library.api.dto.BookRequest;
import com.sda.javakrk24.library.api.dto.BookResponse;
import com.sda.javakrk24.library.api.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookConverter {

    private AuthorRepository authorRepository;

    public BookEntity fromDto(BookRequest bookRequest) {
        BookEntity entity = new BookEntity();
        entity.setTitle(bookRequest.getTitle());
        entity.setAuthors(authorRepository.findAllById(bookRequest.getAuthors()));
        entity.setPages(bookRequest.getPages());
        entity.setPublishYear(bookRequest.getPublishYear());
        entity.setIsbn(bookRequest.getIsbn());
        authorRepository.findAllById(bookRequest.getAuthors())
                .forEach(author -> author.getBooks().add(entity));
        return entity;
    }

    public BookResponse fromDao(BookEntity bookEntity) {
        return BookResponse.builder()
                .authors(bookEntity.getAuthors().stream()
                        .map(authorEntity -> Author
                                .builder()
                                .firstName(authorEntity.getFirstName())
                                .lastName(authorEntity.getLastName())
                                .description(authorEntity.getDescription())
                                .birthYear(authorEntity.getBirthYear())
                                .build())
                        .collect(Collectors.toList()))
                .title(bookEntity.getTitle())
                .pages(bookEntity.getPages())
                .publishYear(bookEntity.getPublishYear())
                .isbn(bookEntity.getIsbn())
                .build();
    }
}
