package com.sda.javakrk24.library.api.repository;

import com.sda.javakrk24.library.api.dao.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
