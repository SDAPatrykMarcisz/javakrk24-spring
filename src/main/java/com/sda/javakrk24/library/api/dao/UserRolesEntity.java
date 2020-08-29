package com.sda.javakrk24.library.api.dao;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "app_users_roles")
public class UserRolesEntity {
    @Id
    @GeneratedValue
    private Long id;
    private UserEntity entity;
    private String role;
}
