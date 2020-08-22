package com.sda.javakrk24.library.api.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "books_author",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") }
    )
    private List<AuthorEntity> authors;

    @Column(name = "pages")
    private int pages;

    @Column(name = "publish_year")
    private int publishYear;

    @Column(name = "isbn")
    private String isbn;

}
