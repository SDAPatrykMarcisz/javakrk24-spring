package com.sda.javakrk24.library.api.repository;

import com.sda.javakrk24.library.api.dao.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldAddBook(){
        //given
        String countQuery = "select count(*) from books";
        assertEquals("0", entityManager.createNativeQuery(countQuery).getSingleResult().toString());

        BookEntity entity = new BookEntity();
        entity.setTitle("Pinokio");

        //when
        BookEntity save = bookRepository.save(entity);
//        bookRepository.flush();

        //then
        assertNotNull(save.getId());
        assertEquals("1", entityManager.createNativeQuery(countQuery).getSingleResult().toString());

    }

}