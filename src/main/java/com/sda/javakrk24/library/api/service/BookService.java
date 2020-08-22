package com.sda.javakrk24.library.api.service;

import com.sda.javakrk24.library.api.dao.BookEntity;
import com.sda.javakrk24.library.api.dto.BookRequest;
import com.sda.javakrk24.library.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;

    @Autowired
    public BookService(BookRepository bookRepository, BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }

    public void saveBook(BookRequest request) {
        bookRepository.save(bookConverter.fromDto(request));
    }

    public List<BookRequest> fetchAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookEntity -> bookConverter.fromDao(bookEntity))
                .collect(Collectors.toList());
    }

    public BookRequest getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookConverter::fromDao)
                .orElseThrow();
    }

    @Transactional
    public void replaceBook(Long id, BookRequest request) {
        BookEntity entityToReplace = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        entityToReplace.setAuthor(request.getAuthor());
        entityToReplace.setTitle(request.getTitle());
        entityToReplace.setPages(request.getPages());
        entityToReplace.setPublishYear(request.getPublishYear());
        entityToReplace.setIsbn(request.getIsbn());
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
