package com.sda.javakrk24.library.api.repository;

import com.sda.javakrk24.library.api.dao.UserEntity;
import com.sda.javakrk24.library.api.dao.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRolesRepository extends JpaRepository<UserRolesEntity, Long> {
}
