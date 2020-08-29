package com.sda.javakrk24.library.api.repository;

import com.sda.javakrk24.library.api.dao.UserEntity;
import com.sda.javakrk24.library.api.dao.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByLoginOrEmail(String login, String email);

    @Query("select role from UserRoleEntity role where role.user.userId = :id")
    List<UserRoleEntity> findUserRoles(@Param("id") Long id);

}
