package com.sda.javakrk24.library.api.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "app_users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long userId;
    private String login;
    private String email;
    private String password;
    private boolean active;
}
