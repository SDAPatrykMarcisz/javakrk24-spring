package com.sda.javakrk24.library.api.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
public class AuthorEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "description")
    private String description;

    @Column(name = "birth_year")
    private long birthYear;

    @ManyToMany(mappedBy = "authors")
    private List<BookEntity> books;
}
