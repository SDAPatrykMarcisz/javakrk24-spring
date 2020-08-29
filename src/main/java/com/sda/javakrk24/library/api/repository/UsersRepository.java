package com.sda.javakrk24.library.api.repository;

import com.sda.javakrk24.library.api.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByLoginOrEmail(String login, String email);

}
