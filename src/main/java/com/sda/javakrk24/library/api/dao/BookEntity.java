package com.sda.javakrk24.library.api.dao;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "pages")
    private int pages;

    @Column(name = "publish_year")
    private int publishYear;

    @Column(name = "isbn")
    private String isbn;

}
