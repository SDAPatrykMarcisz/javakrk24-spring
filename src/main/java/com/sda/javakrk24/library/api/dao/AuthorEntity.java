package com.sda.javakrk24.library.api.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table("authors")
@Data
public class AuthorEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("description")
    private String description;

    @Column("birth_year")
    private long birthYear;

    @ManyToMany(mappedBy = "authors")
    private List<BookEntity> books;
}
